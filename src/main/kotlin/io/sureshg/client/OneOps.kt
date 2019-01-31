package io.sureshg.client

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import net.jodah.failsafe.Failsafe
import net.jodah.failsafe.RetryPolicy
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit.SECONDS

/**
 * OneOps API client
 *
 * @author <a href="mailto:sgopal1@walmartlabs.com">Suresh</a>
 */
class OneOps {

    /**
     * OneOps subsystem services.
     *
     * @param sname service name
     * @param proto service protocol. Default to Http.
     * @param port service port. Default is 8080
     */
    enum class Svc(val sname: String, val proto: String = "http", val port: Int = 8080) {
        CMS(sname = "cmsapi")
    }

    /**
     * OneOps env and it's base urls
     */
    enum class Env(val baseUrl: String) {

        PROD("prod-1312.core.oneops.prod.walmart.com"),
        MGMT("mgmt-1410.core.oneops.prod.walmart.com"),
        STG("stg.core-1612.oneops.prod.walmart.com"),
        DEV("dev-1502.core.oneops.prod.walmart.com"),
        INT("int.oneops.walmart.com"),
        BFD("bfd.dev.cloud.wal-mart.com");

        /**
         * Get env endpoint for the given [service]
         */
        fun endPoint(service: Svc) = "${service.proto}://${service.sname}.$baseUrl:${service.port}/"
    }


    /**
     * OneOps Md classes
     */
    enum class CiClass(val clazz: String) {
        Org("account.Organization"),
        Assembly("account.Assembly"),
        Env("manifest.Environment"),
        Platform("manifest.Platform"),
        Keypair("manifest.Keypair"),
        SecuredBy("bom.SecuredBy"),
        Lb("bom.Lb"),
        OneOpsLb("bom.oneops.1.Lb"),
        Compute("bom.Compute"),
        ComputeMain2("bom.main.2.Compute"),
        ComputeOneOps("bom.oneops.1.Compute");

        companion object {
            fun get(clazz: String) =
                CiClass.values().filter { it.clazz.equals(clazz, true) }.getOrElse(0) { Compute }
        }
    }

    /**
     * OneOps API http client
     */
    class Client {

        companion object {

            val okHttp: OkHttpClient
            val cms: Cms
            // Some naive caching of cids
            val cache = ConcurrentHashMap<Long, String>()
            val retryPolicy: RetryPolicy<Any>

            init {

                okHttp = OkHttpClient.Builder().apply {
                    addInterceptor {
                        val req =
                            it.request().newBuilder().addHeader("Content-Type", "application/json")
                                .build()
                        it.proceed(req)
                    }
                    retryOnConnectionFailure(true)
                    connectTimeout(60, SECONDS)
                    readTimeout(60, SECONDS)
                    writeTimeout(60, SECONDS)

                }.build()

                cms = Retrofit.Builder()
                    .baseUrl(Env.PROD.endPoint(Svc.CMS))
                    .client(okHttp)
                    .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
                    .build()
                    .create(Cms::class.java)

                retryPolicy = RetryPolicy<Any>().apply {
                    handle(IOException::class.java)
                    withDelay(Duration.ofSeconds(1))
                    onFailedAttempt { e -> println("Retry ${e.attemptCount}: Error: ${e.lastFailure}") }
                    withMaxRetries(2)
                }
            }

            /**
             * Get all CIs of the given class [CiClass]
             */
            fun get(
                nsPath: String = "/",
                ciClass: CiClass,
                recursive: Boolean = true,
                decrypt: Boolean = false
            ) = let {
                val params = hashMapOf(
                    "nsPath" to nsPath,
                    "ciClassName" to ciClass.clazz,
                    "recursive" to recursive.toString(),
                    "getEncrypted" to decrypt.toString()
                )
                val res = cms.getCis(params).executeWithRetry(retryPolicy)
                if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                res.body()
            }


            /**
             * Get the compute ci of given IP address. By default the API will query for private_ip.
             */
            fun getCompute(ip: String, public: Boolean = false) = let {
                val ipAttr = if (public) "public_ip" else "private_ip"
                if (ip.isBlank()) throw IllegalArgumentException("$ipAttr can't be blank!")
                val params = hashMapOf(
                    "nsPath" to "/",
                    "recursive" to "true",
                    "attr" to "$ipAttr:eq:$ip"
                )
                val res = cms.getCis(params).executeWithRetry(retryPolicy)
                if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                res.body()!!.getOrElse(0) { mutableMapOf() }
            }

            /**
             * Get the keypair ci for the compute [ciId]
             *
             * @see [getCompute]
             */
            fun getKeyPair(ciId: Long) = let {
                val params = hashMapOf(
                    "ciId" to ciId.toString(),
                    "relationName" to CiClass.SecuredBy.clazz,
                    "direction" to "from",
                    "getEncrypted" to "true"
                )
                val res = cms.getCiRelations(params).executeWithRetry(retryPolicy)
                if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                res.body()!!.mapNotNull { it["toCi"] as? Map<*, *> }
                    .getOrElse(0) { mutableMapOf<String, Any>() }
            }

            /**
             * Get the Platform ci for the compute [ciId]
             *
             * @see [getCompute]
             */
            fun getPlatform(ciId: Long) = let {
                val res = cms.getCi(ciId).executeWithRetry(retryPolicy)
                if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                val nsPath = res.body()!!["nsPath"] as String
                val mnsPath = nsPath.replace("/bom/", "/manifest/", true)
                get(mnsPath, CiClass.Platform)
            }


            /**
             * Get the ciName of the [cid]
             */
            fun getCiName(cid: Long): String {
                var ciName = cache[cid]
                if (ciName == null) {
                    val res = cms.getCi(cid).executeWithRetry(retryPolicy)
                    if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                    ciName = res.body()!!["ciName"] as String
                    cache[cid] = ciName
                }
                return ciName
            }

            /**
             * Get the Platform ci for the compute [ciId]
             *
             * @see [getCompute]
             */
            fun getCi(ciId: Long) = let {
                val res = cms.getCi(ciId).executeWithRetry(retryPolicy)
                if (!res.isSuccessful) throw OneOpsException("Code: ${res.code()}, Message: ${res.errorBody()?.string()}")
                return@let res.body()
            }
        }
    }


    /**
     * Cms API
     */
    interface Cms {

        @GET("/adapter/rest/cm/simple/cis")
        fun getCis(@QueryMap params: Map<String, String>): Call<MutableList<MutableMap<String, Any>>>

        @GET("/adapter/rest/cm/simple/cis/{id}")
        fun getCi(@Path("id") id: Long): Call<MutableMap<String, Any>>

        @GET("/adapter/rest/cm/simple/relations")
        fun getCiRelations(@QueryMap params: Map<String, String>): Call<MutableList<MutableMap<String, Any>>>

        @GET("/adapter/rest/md/classes")
        fun getMdClasses(@QueryMap params: Map<String, String>): Call<MutableList<MutableMap<String, Any>>>
    }

}

/**
 * Exception thrown when communicating to OneOps services.
 */
class OneOpsException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}

/**
 * Date json adapter used for elastic search documents.
 */
class DateJsonAdapter {

    companion object {
        // ES document date formatter
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        // Moshi adapter.
        val moshi = Moshi.Builder().add(DateJsonAdapter()).build()
    }

    @ToJson
    fun toJson(date: Date): String = dateFormatter.format(date)

    @FromJson
    fun fromJson(date: String): Date = dateFormatter.parse(date)
}

/**
 * Execute the [Call] with given [RetryPolicy]
 */
fun <T> Call<T>.executeWithRetry(policy: RetryPolicy<Any>) =
    Failsafe.with(policy).get { -> (if (!isExecuted) execute() else this.clone().execute()) }

