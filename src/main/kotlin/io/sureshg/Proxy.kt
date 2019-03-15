package io.sureshg

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

fun main(args: Array<String>) {

    val foo =
        Proxy.newProxyInstance(
            Foo::class.java.classLoader,
            arrayOf(Foo::class.java),
            InvocationHandler { _, method, _ ->
                return@InvocationHandler "Hellooo ${method.name}"
            }) as Foo

    println(foo.bar())
    println(foo.toString())


    println("Here")
    for (i in 1..10) {
        Thread.yield()
        println("Yielded")
    }

}

interface Foo {
    fun bar(): String
}


data class Book(val author: String, val title: String, val year: Int)