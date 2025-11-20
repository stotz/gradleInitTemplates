plugins {
    java
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.beryx.jlink)
}

group = "{{ @@02|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@03|Application version (e.g. 1.0.0)=1.0.0@@version }}"

repositories {
    mavenCentral()
}

val os = org.gradle.internal.os.OperatingSystem.current()
val jfxClassifier = when {
    os.isWindows -> "win"
    os.isLinux -> "linux"
    os.isMacOsX -> "mac"
    else -> throw GradleException("Unsupported OS for JavaFX")
}

dependencies {
    implementation(libs.javafx.base) { artifact { classifier = jfxClassifier } }
    implementation(libs.javafx.graphics) { artifact { classifier = jfxClassifier } }
    implementation(libs.javafx.controls) { artifact { classifier = jfxClassifier } }
    implementation(libs.javafx.web) { artifact { classifier = jfxClassifier } }

    implementation(libs.controlsfx)
    implementation(libs.formsfx.core) { exclude(group = "org.openjfx") }
    implementation(libs.validatorfx)

    implementation(libs.ikonli.javafx)
    implementation(libs.ikonli.fontawesome5)
    testImplementation(libs.junit)

}

val jdkVersion = libs.versions.jdk.get()
kotlin {
    jvmToolchain(jdkVersion.toInt())
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

application {
    mainClass.set("{{ @@04|Main class (e.g. com.example.MainKt)={{ group }}.MainKt@@main_class }}")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "{{ @@05|Application display name=My JavaFX App@@app_name }}",
            "Implementation-Version" to "{{ version }}",
            "Implementation-Vendor" to "{{ @@06|Vendor/Author name=Example Corp@@vendor }}"
        )
    }
}
