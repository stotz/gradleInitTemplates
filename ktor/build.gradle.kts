plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    application
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test)
}

kotlin {
    jvmToolchain({{ jdk_version }})
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of({{ jdk_version }})
    }
}

application {
    mainClass.set("{{ group }}.ApplicationKt")
}
