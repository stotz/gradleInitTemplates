plugins {
    kotlin("jvm") version "{{ kotlin_version }}"
    kotlin("plugin.spring") version "{{ kotlin_version }}"
    id("org.springframework.boot") version "3.5.7"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "{{ group }}"
version = "{{ version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    
    {% if 'data-jpa' in config('spring_modules', ['web']) %}
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    {% endif %}
    
    {% if 'security' in config('spring_modules', ['web']) %}
    implementation("org.springframework.boot:spring-boot-starter-security")
    {% endif %}
    
    {% if 'actuator' in config('spring_modules', ['web']) %}
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    {% endif %}
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    jvmToolchain({{ jdk_version }})
}

tasks.test {
    useJUnitPlatform()
}
