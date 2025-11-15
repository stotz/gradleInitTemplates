plugins {
    kotlin("jvm") version "{{ kotlin_version }}"
    application
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    
    // Testing
    testImplementation(kotlin("test"))
    testImplementation(libs.junit.jupiter)
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
    mainClass.set("{{ group }}.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "{{ project_name }}",
            "Implementation-Version" to "{{ version }}",
            "Implementation-Vendor" to "{{ config('custom.company', 'Unknown') }}"
        )
    }
}
