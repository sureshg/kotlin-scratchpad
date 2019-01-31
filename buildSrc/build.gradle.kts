plugins {
    `kotlin-dsl`
    // `java-gradle-plugin`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    mavenLocal()
    maven("https://repository.walmart.com/content/repositories/public/")
}

dependencies {
    compileOnly(gradleKotlinDsl())
    compile(kotlin("stdlib-jdk8"))
}