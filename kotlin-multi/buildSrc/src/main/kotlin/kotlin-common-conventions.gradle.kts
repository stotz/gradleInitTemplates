import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

plugins {
    kotlin("jvm")
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Application version (e.g. 1.0.0)=1.0.0@@version }}"

// Access version catalog from main project
val libs = the<VersionCatalogsExtension>().named("libs")
val jdkVersion = libs.findVersion("jdk").get().toString().toInt()

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    // Kotlin 2.x supports max JDK 24 - cap at 24 if configured JDK is newer
    jvmToolchain(minOf(jdkVersion, 24))
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
// Configuration Cache compatible using Provider API
// ============================================================================
val enableGitInfo: Provider<Boolean> = providers
    .gradleProperty("enableGitInfo")
    .map { it.toBoolean() }
    .orElse(false)

// Use providers to get git info at execution time (Configuration Cache compatible)
val gitCommit: Provider<String> = providers.exec {
    commandLine("git", "rev-parse", "--short", "HEAD")
}.standardOutput.asText.map { it.trim().ifEmpty { "unknown" } }

val gitBranch: Provider<String> = providers.exec {
    commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
}.standardOutput.asText.map { it.trim().ifEmpty { "unknown" } }

val gitTag: Provider<String> = providers.exec {
    commandLine("git", "describe", "--tags", "--exact-match")
    isIgnoreExitValue = true
}.standardOutput.asText.map { it.trim().ifEmpty { "none" } }

val gitDirty: Provider<String> = providers.exec {
    commandLine("git", "status", "--porcelain")
}.standardOutput.asText.map { if (it.trim().isEmpty()) "false" else "true" }

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to version.toString()
        )
        
        if (enableGitInfo.get()) {
            attributes(
                "Git-Commit" to gitCommit.get(),
                "Git-Branch" to gitBranch.get(),
                "Git-Tag" to gitTag.get(),
                "Git-Dirty" to gitDirty.get(),
                "Build-Time" to Instant.now().toString(),
                "Build-OS" to "${System.getProperty("os.name")} ${System.getProperty("os.version")}",
                "Build-Host" to InetAddress.getLocalHost().hostName,
                "Build-Jdk" to System.getProperty("java.version"),
                "Built-By" to System.getProperty("user.name")
            )
        }
    }
}
