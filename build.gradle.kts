import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    application
    kotlin("jvm") version "1.2.30"
}

group = "io.sureshg"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "io.sureshg.MainKt"
}

kotlin {
    experimental.coroutines = Coroutines.ENABLE
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.22.5")
}