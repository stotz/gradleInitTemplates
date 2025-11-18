plugins {
    kotlin("jvm")
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Application version (e.g. 1.0.0)=1.0.0@@version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain({{ @@03|(11|17|21)|JDK version=21@@jdk_version }})
}

tasks.test {
    useJUnitPlatform()
}
