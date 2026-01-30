import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

plugins {
    java
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
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

val verboseTests = providers
    .gradleProperty("verboseTests")
    .map { it.toBoolean() }
    .orElse(false)

tasks.test {
    useJUnitPlatform()

    testLogging {
        // ./gradlew test --rerun-tasks
        events("FAILED", "SKIPPED")
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        // ./gradlew test --rerun-tasks -PverboseTests=true
        if (verboseTests.get()) {
            events("PASSED", "FAILED", "SKIPPED", "STANDARD_OUT", "STANDARD_ERROR")
            showStandardStreams = true
        }
    }

    addTestListener(object : org.gradle.api.tasks.testing.TestListener {
        override fun afterSuite(desc: TestDescriptor, result: TestResult) {
            if (desc.parent == null) {
                println(
                    "Test summary: ${result.testCount} tests, " +
                        "${result.successfulTestCount} passed, " +
                        "${result.failedTestCount} failed, " +
                        "${result.skippedTestCount} skipped"
                )
            }
        }

        override fun beforeSuite(desc: TestDescriptor) {}
        override fun beforeTest(desc: TestDescriptor) {}
        override fun afterTest(desc: TestDescriptor, result: TestResult) {}
    })
}

// ============================================================================
// Git Information (optional, enable with -PenableGitInfo=true)
// ============================================================================
val enableGitInfo = providers
    .gradleProperty("enableGitInfo")
    .map { it.toBoolean() }
    .orElse(false)

fun getGitCommit(): String = try {
    val process = ProcessBuilder("git", "rev-parse", "--short", "HEAD")
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    process.inputStream.bufferedReader().readText().trim().ifEmpty { "unknown" }
} catch (e: Exception) {
    "unknown"
}

fun getGitBranch(): String = try {
    val process = ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD")
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    process.inputStream.bufferedReader().readText().trim().ifEmpty { "unknown" }
} catch (e: Exception) {
    "unknown"
}

fun getGitTag(): String = try {
    val process = ProcessBuilder("git", "describe", "--tags", "--exact-match")
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    val result = process.inputStream.bufferedReader().readText().trim()
    if (process.waitFor() == 0 && result.isNotEmpty()) result else "none"
} catch (e: Exception) {
    "none"
}

fun getGitDirty(): String = try {
    val process = ProcessBuilder("git", "status", "--porcelain")
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
    if (process.inputStream.bufferedReader().readText().trim().isEmpty()) "false" else "true"
} catch (e: Exception) {
    "unknown"
}

tasks.jar {
    manifest {
        val baseAttributes = mutableMapOf(
            "Implementation-Title" to "{{ @@05|Application display name=My JavaFX App@@app_name }}",
            "Implementation-Version" to version.toString(),
            "Implementation-Vendor" to "{{ @@06|Vendor/Author name=Example Corp@@vendor }}"
        )

        if (enableGitInfo.get()) {
            baseAttributes["Git-Commit"] = getGitCommit()
            baseAttributes["Git-Branch"] = getGitBranch()
            baseAttributes["Git-Tag"] = getGitTag()
            baseAttributes["Git-Dirty"] = getGitDirty()
            baseAttributes["Build-Time"] = Instant.now().toString()
            baseAttributes["Build-OS"] = "${System.getProperty("os.name")} ${System.getProperty("os.version")}"
            baseAttributes["Build-Host"] = InetAddress.getLocalHost().hostName
            baseAttributes["Build-Jdk"] = System.getProperty("java.version")
            baseAttributes["Built-By"] = System.getProperty("user.name")
        }

        attributes(baseAttributes)
    }
}
