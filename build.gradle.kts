import com.github.jengelman.gradle.plugins.shadow.tasks.*
import com.github.sherter.googlejavaformatgradleplugin.*
import com.google.cloud.tools.jib.image.*
import net.nemerosa.versioning.tasks.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVer: String by System.getProperties()
    val shadowVer: String by System.getProperties()
    val versionsVer: String by System.getProperties()
    val scmVersioning: String by System.getProperties()
    val googleJibVer: String by System.getProperties()
    val springDepVer: String by System.getProperties()
    val javaFmtVer: String by System.getProperties()

    application
    idea
    jacoco
    `help-tasks`

    kotlin("jvm") version kotlinVer
    kotlin("kapt") version kotlinVer
    id("com.github.johnrengelman.shadow") version shadowVer
    id("com.github.ben-manes.versions") version versionsVer
    id("net.nemerosa.versioning") version scmVersioning
    id("com.google.cloud.tools.jib") version googleJibVer
    id("io.spring.dependency-management") version springDepVer
    id("com.github.sherter.google-java-format") version javaFmtVer
    id("org.sonarqube") version "2.6.2"
}

val gradleVer: String by System.getProperties()
val ktCompatVer: String by System.getProperties()
val javaCompatVer: String by System.getProperties()
val graalvmVer: String by System.getProperties()
val coroutinesVer: String by System.getProperties()
val javaCompilerArgs = listOf("-Xlint:all", "-parameters")
val ktCompilerArgs = listOf("-Xjsr305=strict", "-Xprogressive")

group = "io.sureshg"
version = "0.2.0"
description = "Kotlin scratchpad"

application {
    mainClassName = "io.sureshg.MainKt"
}

kotlin {
    experimental.coroutines = ENABLE
}

java {
    sourceCompatibility = JavaVersion.toVersion(javaCompatVer)
    targetCompatibility = JavaVersion.toVersion(javaCompatVer)
}

jib {
    to {
        val tag = project.findProperty("tag") ?: project.version
        image = "sureshg/kotlin-demo:$tag"
        credHelper = "osxkeychain"
        auth {
            username = System.getenv("JIB_TO_USER")
            password = System.getenv("JIB_TO_PASSWORD")
        }
    }
    container {
        jvmFlags = listOf(
            "-server",
            "-Xms256m",
            "-XX:+UseG1GC",
            "-Djava.security.egd=file:/dev/./urandom"
        )
        mainClass = application.mainClassName
        args = listOf(project.description, project.version.toString())
        ports = listOf("8080-8090/tcp")
        useCurrentTimestamp = true
        setFormat(ImageFormat.Docker)
    }
    setExtraDirectory(File("src/main/resources"))
}

tasks {
    /** Java */
    withType<JavaCompile> {
        options.apply {
            encoding = "UTF-8"
            isIncremental = true
            compilerArgs.addAll(javaCompilerArgs)
        }
    }

    /** Kotlin */
    val options: KotlinCompile.() -> Unit = {
        kotlinOptions {
            verbose = true
            jvmTarget = ktCompatVer
            javaParameters = true
            freeCompilerArgs += ktCompilerArgs
        }
    }

    val compileKotlin by getting(KotlinCompile::class, options)
    val compileTestKotlin by getting(KotlinCompile::class, options)

    /** Shading */
    withType<ShadowJar> {
        description = "Create a fat JAR of $archiveName and runtime dependencies."
        doLast {
            println("FatJar: ${archivePath.path} (${archivePath.length().toDouble() / (1_000 * 1_000)} MB)")
        }
    }

    /** Code Coverage */
    withType<JacocoReport> {
        this.reports {
            xml.isEnabled = true
            html.isEnabled = false
            csv.isEnabled = false
        }
        val jacocoTestReport by tasks
        jacocoTestReport.dependsOn("test")
    }

    /** JUnit 5 */
    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        reports.html.isEnabled = true
    }

    /** SCM Versioning */
    withType<VersionFileTask> {
        file = buildDir.resolve("version.properties")
        prefix = "VERSION_"
    }

    /** Google java format*/
    withType<GoogleJavaFormat> {
        tasks.getByName("build").dependsOn(this)
    }

    /** Gradle Wrapper */
    getByName<Wrapper>("wrapper") {
        gradleVersion = gradleVer
        distributionType = Wrapper.DistributionType.ALL
    }

    defaultTasks("clean", "tasks", "--all")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", coroutinesVer)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", coroutinesVer)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-nio", coroutinesVer)
    // implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-reactor", coroutinesVer)

    // HTTPS + JSON
    implementation("com.squareup.retrofit2", "retrofit", "2.4.0")
    implementation("com.squareup.okhttp3", "okhttp", "3.11.0")
    implementation("com.squareup.okhttp3", "okhttp-tls", "3.11.0")
    implementation("com.squareup.moshi", "moshi", "1.6.0")
    implementation("com.jakewharton.retrofit", "retrofit2-reactor-adapter", "2.1.0")
    implementation(
        "com.jakewharton.retrofit",
        "retrofit2-kotlin-coroutines-experimental-adapter",
        "1.0.0"
    )
    kapt("com.squareup.moshi", "moshi-kotlin-codegen", "1.6.0")

    // Logging
    implementation("org.slf4j", "slf4j-api", "1.7.25")
    runtimeOnly("org.slf4j", "slf4j-simple", "1.7.25")

    // Misc
    implementation("com.github.ajalt", "clikt", "1.5.0")
    implementation("net.jodah", "failsafe", "1.1.0")
    compileOnly("com.google.code.findbugs", "jsr305", "3.0.2")
    compileOnly("org.graalvm", "graal-sdk", graalvmVer)

    // JUnit5
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.3.0-RC1")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.3.0-RC1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.3.0-RC1")
    testImplementation("org.assertj", "assertj-core", "3.11.0")

    // Mock
    testImplementation("org.mockito", "mockito-core", "2.21.0")
    testImplementation("com.nhaarman.mockitokotlin2", "mockito-kotlin", "2.0.0-RC1")

    // Test web server
    testImplementation("com.squareup.okhttp3", "mockwebserver", "3.11.0")
}


