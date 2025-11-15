plugins {
    kotlin("jvm")
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain({{ jdk_version }})
}

tasks.test {
    useJUnitPlatform()
}
