import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE

plugins {
    application
    kotlin("jvm") version "1.2.30"
}

application {
    mainClassName = "io.sureshg.MainKt"
}

kotlin {
    experimental.coroutines = ENABLE
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib"))
}

