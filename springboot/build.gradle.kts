plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.boot.starter.web)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.kotlin.reflect)
    
    testImplementation(libs.spring.boot.starter.test)
}

kotlin {
    jvmToolchain({{ jdk_version }})
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of({{ jdk_version }})
    }
}

tasks.test {
    useJUnitPlatform()
}
