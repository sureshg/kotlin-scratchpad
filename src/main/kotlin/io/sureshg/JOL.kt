package io.sureshg

import org.openjdk.jol.info.GraphLayout
import org.openjdk.jol.vm.VM

fun main(args: Array<String>) {
    val name = "Suresh"
    println(VM.current().details())
    println(GraphLayout.parseInstance(name).toPrintable())
}