plugins {
    `kotlin-dsl`
    // `java-gradle-plugin`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    // maven("https://repo.xxxx.com")
    mavenCentral()
}

dependencies {
    compileOnly(gradleKotlinDsl())
    implementation(kotlin("stdlib-jdk8"))
}