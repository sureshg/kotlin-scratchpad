@file:Suppress("unused")

import org.gradle.kotlin.dsl.*
import org.gradle.plugin.use.*

object Versions {
    // Deps
    const val kotlin = "1.3.21"
    const val coroutines = "1.1.1"
    const val kotlinSerialization = "0.10.0"
    const val kotlinxReflectLite = "1.0.0"
    const val kotlinImmutableColl = "0.1"
    const val kotlinIO = "0.1.4"
    const val jsr305 = "3.0.2"
    const val clikt = "1.6.0"
    const val moshi = "1.8.0"
    const val okhttp = "3.13.1"
    const val okio = "2.2.0"
    const val ok2Curl = "0.4.5"
    const val retrofit = "2.5.0"
    const val commonsCodec = "1.11"
    const val rsocket = "0.11.16"
    const val failsafe = "2.0.1"
    const val assertj = "3.11.1"
    const val graal = "1.0.0-rc12"
    const val junit = "5.4.0"
    const val mockito = "2.24.0"
    const val awaitility = "3.1.6"
    const val sshj = "0.26.0"
    const val jol = "0.9"
    const val slf4j = "1.7.25"
    const val mockitoKotlin = "2.1.0"
    const val conscrypt = "1.4.1"
    const val tink = "1.2.1"
    const val byteBuddy = "1.9.7"
    const val loggingRetrofit = "0.10.0"
    const val bouncyCastle = "1.60"
    const val brotli = "0.1.2"
    const val commonsIO = "2.6"
    const val jnrJffi = "1.2.18"
    const val jnrUnixSocket = "0.21"
    const val netty = "4.1.32.Final"
    const val retrofitCoroutinesAdapter = "0.9.2"
    const val retrofitSerializationAdapter = "0.2.0"
    const val retrofitReactorAdapter = "2.1.0"
    const val ztExec = "1.10"
    const val jline = "3.9.0"
    const val eclipseCollections = "9.2.0"
    const val trov4j = "1.0.20181211"
    const val japicmp = "0.13.0"
    const val jjwt = "0.10.5"
    const val jkeychain = "1.0.0"
    const val openpdf = "1.2.9"
    const val orsonpdf = "1.8"
    const val jfreesvg = "3.3"
    const val jibCore = "0.1.1"
    const val swaggerUI = "3.10.0"
    const val swaggerCodegenCli = "3.0.0"
    const val smbj = "0.9.1"
    const val asn1 = "0.1.0"
    const val caffeine = "2.6.2"
    const val cache2k = "1.2.0.Final"
    const val kolor = "0.0.2"
    const val ktlint = "0.30.0"
    const val mockk = "1.9.1"
    const val reactiveStreams = "1.0.2"
    const val reactor = "3.2.5.RELEASE"
    const val ff4j = "1.3.0"
    const val jmh = "1.21"

    // Plugins
    const val gradle = "5.2.1"
    const val ktlintPlugin = "7.1.0"
    const val dokka = "0.9.17"
    const val shadow = "4.0.4"
    const val googleJib = "1.0.0"
    const val sonarqube = "2.7"
    const val nemerosaVersioning = "2.8.2"
    const val springDepMgmt = "1.0.6.RELEASE"
    const val buildSrcVersions = "0.3.2"
    const val googleJavaFormat = "0.8"
    const val benmanesVersions = "0.20.0"
    const val buildScanPlugin = "2.0.2"
    const val gitPublishPlugin = "1.0.1"
    const val swaggerGen = "2.16.0"
    const val gitProperties = "2.0.0"
    const val gradleRelease = "2.8.0"
    const val mavenPublishAuth = "2.0.2"
    const val javafxPlugin = "0.0.7"
    const val jmhPlugin = "0.4.8"
}

object Deps {
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val kotlinxReflectLite = "org.jetbrains.kotlinx:kotlinx.reflect.lite:${Versions.kotlinxReflectLite}"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val kotlinSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerialization}"
    const val kotlinIO = "org.jetbrains.kotlinx:kotlinx-io-jvm:${Versions.kotlinIO}"
    const val kotlinImmutableColl =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.kotlinImmutableColl}"

    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.coroutines}"
    const val coroutinesReactor = "org.jetbrains.kotlinx:coroutines-reactor:${Versions.coroutines}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val coroutinesDebug = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:${Versions.coroutines}"

    const val okio = "com.squareup.okio:okio:${Versions.okio}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpMWS = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    const val okhttpSse = "com.squareup.okhttp3:okhttp-sse:${Versions.okhttp}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpTls = "com.squareup.okhttp3:okhttp-tls:${Versions.okhttp}"
    const val okhttpUDS = "com.squareup.okhttp3.sample:unixdomainsockets:${Versions.okhttp}"
    const val okhttpDigest = "com.baulsupp:okhttp-digest:0.4.0"
    const val okhttpDoh = "com.squareup.okhttp3:okhttp-dnsoverhttps:${Versions.okhttp}"
    const val ok2Curl = "com.github.mrmike:ok2curl:${Versions.ok2Curl}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofitLogging = "com.nightlynexus.logging-retrofit:logging:${Versions.loggingRetrofit}"
    const val retrofitReactorAdapter =
        "com.jakewharton.retrofit:retrofit2-reactor-adapter:${Versions.retrofitReactorAdapter}"
    const val retrofitCoroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutinesAdapter}"
    const val retrofitSerializationAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitSerializationAdapter}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"

    const val bouncyCastle = "org.bouncycastle:bcprov-jdk15on:${Versions.bouncyCastle}"
    const val conscryptUber = "org.conscrypt:conscrypt-openjdk-uber:${Versions.conscrypt}"
    const val tink = "com.google.crypto.tink:tink:${Versions.tink}"
    const val sshj = "com.hierynomus:sshj:${Versions.sshj}"
    const val smbj = "com.hierynomus:smbj:${Versions.smbj}"
    const val asn1 = "com.hierynomus:asn-one:${Versions.asn1}"

    const val slf4jApi = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val slf4jSimple = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val slf4jNop = "org.slf4j:slf4j-nop:${Versions.slf4j}"

    const val caffeine = "com.github.ben-manes.caffeine:caffeine:${Versions.caffeine}"
    const val cache2kApi = "org.cache2k:cache2k-api:${Versions.cache2k}"
    const val cache2kCore = "org.cache2k:cache2k-core:${Versions.cache2k}"

    const val jibCore = "com.google.cloud.tools:jib-core:${Versions.jibCore}"
    const val failsafe = "net.jodah:failsafe:${Versions.failsafe}"
    const val nettyResolveDns = "io.netty:netty-resolver-dns:${Versions.netty}"
    const val jffi = "com.github.jnr:jffi:${Versions.jnrJffi}"
    const val jnrUnixSocket = "com.github.jnr:jnr-unixsocket:${Versions.jnrUnixSocket}"
    const val commonsIO = "commons-io:commons-io:${Versions.commonsIO}"
    const val commonsCodec = "commons-codec:commons-codec:${Versions.commonsCodec}"
    const val jjwt = "io.jsonwebtoken:jjwt-api:${Versions.jjwt}"
    const val jjwtImpl = "io.jsonwebtoken:jjwt-impl:${Versions.jjwt}"
    const val jjwtJackson = "io.jsonwebtoken:jjwt-jackson:${Versions.jjwt}"
    const val jkeychain = "pt.davidafsilva.apple:jkeychain:${Versions.jkeychain}"
    const val jfreesvg = "org.jfree:jfreesvg:${Versions.jfreesvg}"
    const val orsonpdf = "com.orsonpdf:orsonpdf:${Versions.orsonpdf}"
    const val openpdf = "com.github.librepdf:openpdf:${Versions.openpdf}"
    const val jsr305 = "com.google.code.findbugs:jsr305:${Versions.jsr305}"

    const val clikt = "com.github.ajalt:clikt:${Versions.clikt}"
    const val jline = "org.jline:jline:${Versions.jline}"
    const val jlineTerminal = "org.jline:jline-terminal:${Versions.jline}"
    const val jlineReader = "org.jline:jline-reader:${Versions.jline}"
    const val ztExec = "org.zeroturnaround:zt-exec:${Versions.ztExec}"
    const val brotli = "org.brotli:dec:${Versions.brotli}"
    const val byteBuddy = "net.bytebuddy:byte-buddy:${Versions.byteBuddy}"
    const val jol = "org.openjdk.jol:jol-core:${Versions.jol}"
    const val japicmp = "com.github.siom79.japicmp:japicmp:${Versions.japicmp}"
    const val kolor = "com.andreapivetta.kolor:kolor:${Versions.kolor}"

    const val reactiveStreams = "org.reactivestreams:reactive-streams:${Versions.reactiveStreams}"
    const val reactiveStreamsFlowAdapters =
        "org.reactivestreams:reactive-streams-flow-adapters:${Versions.reactiveStreams}"
    const val reactorCore = "io.projectreactor:reactor-core:${Versions.reactor}"
    const val reactorTest = "io.projectreactor:reactor-test:${Versions.reactor}"

    const val eclipseCollections = "org.eclipse.collections:eclipse-collections:${Versions.eclipseCollections}"
    const val eclipseCollectionsApi = "org.eclipse.collections:eclipse-collections-api:${Versions.eclipseCollections}"
    const val trov4j = "org.jetbrains.intellij.deps:trove4j:${Versions.trov4j}"
    const val ff4j = "org.ff4j:ff4j-core:${Versions.ff4j}"
    const val jmhCore = "org.openjdk.jmh:jmh-core:${Versions.jmh}"
    const val jmhGenAnnprocess = "org.openjdk.jmh:jmh-generator-annprocess:${Versions.jmh}"

    const val graalSdk = "org.graalvm.sdk:graal-sdk:${Versions.graal}"
    const val graalCompiler = "org.graalvm.compiler:compiler:${Versions.graal}"
    const val substratevm = "com.oracle.substratevm:svm:${Versions.graal}"
    const val substratevmLibSupport = "com.oracle.substratevm:library-support:${Versions.graal}"

    const val rsocketCore = "io.rsocket:rsocket-core:${Versions.rsocket}"
    const val rsocketNetty = "io.rsocket:rsocket-transport-netty:${Versions.rsocket}"

    const val swaggerUI = "org.webjars:swagger-ui:${Versions.swaggerUI}"
    const val swaggerCodegenCli = "io.swagger.codegen.v3:swagger-codegen-cli:${Versions.swaggerCodegenCli}"
    const val junitJupiter = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val junitJupiterParams = "org.junit.jupiter:junit-jupiter-params:${Versions.junit}"
    const val assertjCore = "org.assertj:assertj-core:${Versions.assertj}"
    const val awaitility = "org.awaitility:awaitility:${Versions.awaitility}"
    const val awaitilityKotlin = "org.awaitility:awaitility-kotlin:${Versions.awaitility}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
}

/**
 * PluginId Extensions
 */
inline val PluginDependenciesSpec.springDepMgmt get() = id("io.spring.dependency-management") version Versions.springDepMgmt
inline val PluginDependenciesSpec.kotlinJvm get() = kotlin("jvm") version Versions.kotlin
inline val PluginDependenciesSpec.kotlinKapt get() = kotlin("kapt") version Versions.kotlin
inline val PluginDependenciesSpec.kotlinNoArg get() = kotlin("plugin.noarg") version Versions.kotlin
inline val PluginDependenciesSpec.kotlinAllOpen get() = kotlin("plugin.allopen") version Versions.kotlin
inline val PluginDependenciesSpec.kotlinMultiplatform get() = kotlin("multiplatform") version Versions.kotlin
inline val PluginDependenciesSpec.kotlinSerialization get() = kotlin("serialization") version Versions.kotlin
inline val PluginDependenciesSpec.benmanesVersions get() = id("com.github.ben-manes.versions") version Versions.benmanesVersions
inline val PluginDependenciesSpec.ktlint get() = id("org.jlleitschuh.gradle.ktlint") version Versions.ktlintPlugin
inline val PluginDependenciesSpec.gitProperties get() = id("com.gorylenko.gradle-git-properties") version Versions.gitProperties
inline val PluginDependenciesSpec.dokka get() = id("org.jetbrains.dokka") version Versions.dokka
inline val PluginDependenciesSpec.googleJavaFormat get() = id("com.github.sherter.google-java-format") version Versions.googleJavaFormat
inline val PluginDependenciesSpec.gradleRelease get() = id("net.researchgate.release") version Versions.gradleRelease
inline val PluginDependenciesSpec.mavenPublishAuth get() = id("org.datlowe.maven-publish-auth") version Versions.mavenPublishAuth
inline val PluginDependenciesSpec.javafx get() = id("org.openjfx.javafxplugin") version Versions.javafxPlugin
inline val PluginDependenciesSpec.jmh get() = id("me.champeau.gradle.jmh") version Versions.jmhPlugin
inline val PluginDependenciesSpec.shadow get() = id("com.github.johnrengelman.shadow") version Versions.shadow
inline val PluginDependenciesSpec.buildSrcVersions get() =  id("de.fayard.buildSrcVersions") version Versions.buildSrcVersions
inline val PluginDependenciesSpec.swaggerGenerator get() = id("org.hidetake.swagger.generator") version Versions.swaggerGen
inline val PluginDependenciesSpec.googleJib get() =  id("com.google.cloud.tools.jib") version Versions.googleJib
inline val PluginDependenciesSpec.sonarqube get() = id("org.sonarqube") version Versions.sonarqube