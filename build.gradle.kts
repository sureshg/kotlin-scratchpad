import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    java
    application
    idea
    kotlinJvm
    kotlinKapt
    kotlinxSerialization
    dokka
    shadow
    benmanesVersions
    buildSrcVersions
    ktlint
    swaggerGenerator
    gitProperties
    googleJib
    gradleRelease
    springDepMgmt
    googleJavaFormat
    sonarqube
    jacoco
    `maven-publish`
    signing
    distribution
    // kotlinNoArg
    // kotlinAllOpen
    // kotlinMultiplatform
    // mavenPublishAuth
}

group = "io.sureshg"
description = "Kotlin scratchpad"

val shaded: String by project
val gitUrl = "https://github.com/sureshg/kotlin-scratchpad"

application {
    mainClassName = "io.sureshg.MainKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

ktlint {
    version.set(Versions.ktlint)
    verbose.set(false)
    ignoreFailures.set(true)
}

googleJavaFormat {
    toolVersion = Versions.Google.javaFormat
}

idea {
    module {
        sourceDirs.addAll(
            files(
                "build/generated/source/kapt/main",
                "build/generated/source/kaptKotlin/main"
            )
        )
        generatedSourceDirs.addAll(
            files(
                "build/generated/source/kapt/main",
                "build/generated/source/kaptKotlin/main"
            )
        )
    }
}

jib {
    to {
        image = "sureshg/kotlin-demo"
        tags = setOf(project.version.toString(), "latest")
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
    }

    extraDirectories { File("src/main/resources") }
}

gitProperties {
    gitPropertiesDir = "${project.buildDir}/resources/main/META-INF/${project.name}"
    customProperties["kotlin"] = Versions.kotlin
}

release {
    revertOnFail = true
}

tasks {
    // Java main and test
    withType<JavaCompile> {
        options.apply {
            encoding = "UTF-8"
            isIncremental = true
            compilerArgs.addAll(listOf("-Xlint:all", "-parameters"))
        }
    }

    // Kotlin main and test
    withType<KotlinCompile> {
        kotlinOptions {
            verbose = true
            jvmTarget = "1.8"
            javaParameters = true
            freeCompilerArgs += listOf(
                "-Xjsr305=strict",
                "-Xjvm-default=enable",
                "-XXLanguage:+InlineClasses",
                "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
                "-progressive"
            )
        }
    }

    // JUnit5
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        reports.html.isEnabled = true
    }

    // Distribution
    distTar {
        compression = Compression.GZIP
    }

    // Shaded jar
    val relocateShadowJar by registering(ConfigureShadowRelocation::class) {
        prefix = "shadow"
        target = shadowJar.get()
        description = "Automatically relocating all the dependencies with '$prefix' prefix."

        doFirst {
            println(description)
        }
    }

    // Uber jar
    shadowJar {
        archiveClassifier.set("uber")
        description = "Create a fat JAR of $archiveFileName and runtime dependencies."
        doLast {
            val fatJar = archiveFile.get().asFile
            println("FatJar: ${fatJar.path} (${fatJar.length().toDouble() / (1_000 * 1_000)} MB)")
        }

        if (shaded.toBoolean()) {
            dependsOn(relocateShadowJar)
        }
    }

    // Javadoc
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("javadoc"))
        dokkaSourceSets.configureEach {
            jdkVersion.set(8)
            noStdlibLink.set(false)
            noJdkLink.set(false)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("$gitUrl/tree/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }

    // Code Coverage
    jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = false
            csv.isEnabled = false
        }
        dependsOn(":test")
    }

    // Google java format
    this.googleJavaFormat {
        dependsOn(":build")
    }

    // Release depends on publish.
    afterReleaseBuild {
        dependsOn(":publish")
    }

    // Generate pom
    withType<GenerateMavenPom> {
        destination = file("$buildDir/libs/${jar.get().archiveBaseName}.pom")
    }

    // Gradle Wrapper
    wrapper {
        gradleVersion = Versions.gradle
        distributionType = Wrapper.DistributionType.ALL
    }

    defaultTasks("clean", "tasks", "--all")
}

// Sources jar
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

// Javadoc jar
val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

// Tar distribution
distributions {
    getByName("main") {
        contents {
            from("${rootProject.projectDir}") {
                include("README.md", "LICENSE")
            }
            from("${rootProject.projectDir}/src/main/scripts") {
                fileMode = Integer.parseUnsignedInt("755", 8)
                into("bin")
            }
            from("${rootProject.projectDir}/pkg") {
                into("pkg")
            }
            into("lib") {
                from(tasks.jar)
            }
            into("lib") {
                from(configurations.runtimeClasspath)
            }
        }
    }
}

// SourceSets
// println(sourceSets.main.get().allSource.srcDirs)
// println(kotlin.sourceSets.test.get().kotlin.srcDirs)

publishing {
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("$buildDir/repo")
        }
    }
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            // artifact(tasks.jar.get())
            artifact(tasks.shadowJar.get())
            artifact(sourcesJar.get())
            // artifact(javadocJar.get())
            artifact(tasks.distTar.get())

            // pom.addDependencies(project)
            pom {
                packaging = "jar"
                description.set(project.description)
                inceptionYear.set("2019")
                url.set(gitUrl)

                developers {
                    developer {
                        id.set("sureshg")
                        name.set("Suresh")
                        email.set("contact@sureshg.io")
                        url.set("https://sureshg.io")
                    }
                }

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                scm {
                    url.set(gitUrl)
                    tag.set("HEAD")
                    connection.set("scm:git:$gitUrl.git")
                    developerConnection.set("scm:git:$gitUrl.git")
                }

                issueManagement {
                    system.set("github")
                    url.set("$gitUrl/issues")
                }
            }
        }
    }
}

repositories {
    // maven(KotlinEap.url)
    mavenCentral()
    jcenter()
    maven(Koltinx.url)
}

// Overriding group of dependencies.
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "xx.xx") {
            useVersion("0.0.1")
            because("Using this version.")
        }
    }
}

dependencies {
    implementation(Deps.Kotlin.stdLibJdk8)
    // Import a BOM
    // implementation(enforcedPlatform())
    implementation(Deps.Kotlinx.coroutinesCore)
    implementation(Deps.Kotlinx.coroutinesJdk8)

    // HTTPS + JSON
    implementation(Deps.OkHttp.okhttp)
    implementation(Deps.OkHttp.tls)
    implementation(Deps.Moshi.moshi)
    implementation(Deps.Moshi.adapters)
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.kotlinCoroutinesAdapter)
    implementation(Deps.Retrofit.logging)
    implementation(Deps.Apache.commonsCodec)
    implementation(Deps.Retrofit.converterMoshi)
    implementation(Deps.Kotlinx.serialization)
    kapt(Deps.Moshi.kotlinCodegen)

    // Logging
    implementation(Deps.Log.slf4jApi)
    runtimeOnly(Deps.Log.slf4jSimple)

    // Misc
    implementation(Deps.rsocketCore)
    implementation(Deps.rsocketNetty)
    implementation(Deps.jol)
    implementation(Deps.Cli.clikt)
    implementation(Deps.failsafe)
    implementation(Deps.classgraph)
    compileOnly(Deps.Google.jsr305)
    compileOnly(Deps.Graal.sdk)
    compileOnly(Deps.Graal.substratevm)

    // JUnit5
    testImplementation(Deps.JUnit.jupiter)
    testImplementation(Deps.assertjCore)

    // Mock
    testImplementation(Deps.Mock.mockito)
    testImplementation(Deps.Mock.mockitoKotlin)
    testImplementation(Deps.Mock.mockk)
    testImplementation(Deps.OkHttp.mockWebServer)
}
