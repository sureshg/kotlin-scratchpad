package io.sureshg.client

import java.io.File
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

fun main(args: Array<String>) {


    val lb = OneOps.Client.get(nsPath = "/", ciClass = OneOps.CiClass.Lb) ?: emptyList<MutableMap<String, Any>>()
    println("Found ${lb.size} Lb instance")
    val oneopsLb =
        OneOps.Client.get(nsPath = "/", ciClass = OneOps.CiClass.OneOpsLb) ?: emptyList<MutableMap<String, Any>>()
    println("Found ${oneopsLb.size} OneOps instance")
    val lbs = lb + oneopsLb

    println("Total: ${lbs.size}")

    val cache = ConcurrentHashMap<Long, String>()

    val list = lbs.filter {

        val ciName = it["ciName"] as String
        println(ciName)
        val cloudId = try {
            ciName.split("-")[1].toLong()
        } catch (e: Exception) {
            return@filter false
        }
        val cloudName = cache.computeIfAbsent(cloudId) { ciId ->
            try {
                val cloudCi = OneOps.Client.getCi(ciId)!!
                cloudCi["ciName"] as String
            } catch (e: Exception) {
                println(e.message)
                ""
            }
        }

        cloudName.contains("azure", true) || cloudName.contains("az", true)
    }.map {
        val ciName = it["ciName"] as String
        val s = cache[ciName.split("-")[1].toLong()] + "," + it["ciAttributes"]
        println(s)
        s
    }

    println("Found ${list.size}")

    File("/Users/sgopal1/Desktop/prod.csv").writeText(list.joinToString())


    AtomicLong(10).compareAndSet(10,20);

}