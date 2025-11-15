plugins {
    kotlin("jvm") version "{{ kotlin_version }}"
    kotlin("plugin.serialization") version "{{ kotlin_version }}"
    application
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.3"

dependencies {
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    
    {% if 'auth' in config('ktor_features', ['serialization']) %}
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    {% endif %}
    
    {% if 'websockets' in config('ktor_features', ['serialization']) %}
    implementation("io.ktor:ktor-server-websockets:$ktorVersion")
    {% endif %}
    
    implementation("ch.qos.logback:logback-classic:1.5.15")
    
    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
}

kotlin {
    jvmToolchain({{ jdk_version }})
}

application {
    mainClass.set("{{ group }}.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}
