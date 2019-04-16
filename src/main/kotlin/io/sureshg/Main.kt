package io.sureshg

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.sureshg.json.DefaultJsonDataAdapter
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.list
import java.util.*
import kotlin.system.measureTimeMillis

// object Seq {
//     val fib = sequence {
//         var a = 0
//         var b = 1

//         while (true) {
//             yield(b)
//             val next = a + b
//             a = b
//             b = next
//         }

//     }
// }

fun main(args: Array<String>) {
    println(args.joinToString(" "))
    //Seq.fib.take(10).forEach(::println)

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

    println(toHex("This is test message".toByteArray()))

    println("----- Kotlinx Serialization ----")
    // serializing objects
    val jsonData = Json.stringify(Data.serializer(), Data(42))
    // serializing lists
    val jsonList = Json.stringify(Data.serializer().list, listOf(Data(42)))
    println(jsonData) // {"a": 42, "b": "42"}
    println(jsonList) // [{"a": 42, "b": "42"}]

    // parsing data back
    val obj = Json.parse(Data.serializer(), """{"a":42,"b":120}""")
    println(obj) // Data(a=42, b="42")
}

@Serializable
data class Data(val a: Int, val b: String = "35")

@JsonClass(generateAdapter = true)
data class Blog(
    val id: Int,
    val name: String,
    val desc: String = "",
    val content: String? = null,
    val created: Date?
)

@JsonClass(generateAdapter = true)
data class Blog1(
    val id: Int,
    val name: String,
    val desc: String = "",
    val content: String? = null,
    val created: Date?
)

@JsonClass(generateAdapter = true)
data class Blog2(
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