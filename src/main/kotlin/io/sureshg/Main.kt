package io.sureshg

import kotlin.coroutines.experimental.*


val fib = buildSequence {
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
}