import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    val kotlinVer: String by System.getProperties()
    application
    kotlin("jvm") version kotlinVer
}

val javaVer: String by System.getProperties()
val coroutinesVer: String by System.getProperties()

group = "io.sureshg"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "io.sureshg.MainKt"
}

kotlin {
    experimental.coroutines = ENABLE
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = javaVer
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVer)
}