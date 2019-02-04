plugins {
    `kotlin-dsl`
    // `java-gradle-plugin`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    // maven("https://repo.xxxx.com/content/repositories/public/")
    mavenCentral()
}

dependencies {
    compileOnly(gradleKotlinDsl())
    compile(kotlin("stdlib-jdk8"))
}