import com.github.jengelman.gradle.plugins.shadow.tasks.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVer: String by System.getProperties()
    val shadowVer: String by System.getProperties()
    val versionsVer: String by System.getProperties()

    application
    kotlin("jvm") version kotlinVer
    id("com.github.johnrengelman.shadow") version shadowVer
    id("com.github.ben-manes.versions") version versionsVer
}

val javaVer: String by System.getProperties()
val gradleVer: String by System.getProperties()
val coroutinesVer: String by System.getProperties()

group = "io.sureshg"
version = "0.0.1"

application {
    mainClassName = "io.sureshg.MainKt"
}

kotlin {
    experimental.coroutines = ENABLE
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        verbose = true
        jvmTarget = javaVer
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xprogressive")
    }
}

val compileTestKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        verbose = true
        jvmTarget = javaVer
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xprogressive")
    }
}

tasks.withType<ShadowJar> {
    description = "Create a fat JAR of $archiveName and runtime dependencies."
    doLast {
        println("FatJar: ${archivePath.path} (${archivePath.length().toDouble() / (1_000 * 1_000)} MB)")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVer)
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", coroutinesVer)
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-nio", coroutinesVer)
    // compile("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor", coroutinesVer)
}

task<Wrapper>("wrapper") {
    gradleVersion = gradleVer
    distributionType = Wrapper.DistributionType.ALL
}
defaultTasks("clean", "tasks", "--all")