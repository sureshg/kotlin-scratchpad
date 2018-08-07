package io.sureshg

fun main(args: Array<String>) {
    val multiString = "\\d\\\\\\d".toRegex(RegexOption.IGNORE_CASE)
    println("\\d\\\\\\d")
    println(multiString.matches("2\\3"))
}