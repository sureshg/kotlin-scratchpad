package io.sureshg

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.sureshg.json.DefaultJsonDataAdapter
import java.util.*
import kotlin.system.measureTimeMillis


val fib = sequence {
    var a = 0
    var b = 1

    while (true) {
        yield(b)
        val next = a + b
        a = b
        b = next
    }

}

fun main(args: Array<String>) {
    println(args.joinToString(" "))
    fib.take(10).forEach(::println)

    val moshi = Moshi.Builder().add(
        DefaultJsonDataAdapter.newFactory(
            Date::class.java,
            Date()
        )
    ).add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).build()

    val adapter = moshi.adapter(Blog::class.java).nullSafe()

    println(measureTimeMillis {
        for (i in 1..10) {
            println(
                adapter.fromJson(
                    """
                {
                "id" : 10,
                "name" : "test",
                "desc" : "dsds",
                "created" : null
                }
               """.trimIndent()
                )
            )
        }
    })

}


@JsonClass(generateAdapter = true)
data class Blog(
    val id: Int,
    val name: String,
    val desc: String = "",
    val content: String? = null,
    val created: Date?
)

private val hexArray = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')
fun toHex(bytes: ByteArray): String {

    val hexChars = CharArray(bytes.size shl 1)
    for (i in bytes.indices) {
        val v = bytes[i].toInt() and 0xFF
        hexChars[i * 2] = hexArray[v ushr 4]
        hexChars[i * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}