import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

// ============================================================================
// Base conventions for every JVM module (Java and Kotlin alike).
// kotlin-common-conventions builds on top of this plugin.
// ============================================================================
plugins {
    java
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Application version (e.g. 1.0.0)=1.0.0@@version }}"

// Access version catalog from main project
val libs = the<VersionCatalogsExtension>().named("libs")
val jdkVersion = libs.findVersion("jdk").get().toString().toInt()

java {
    // Project toolchain from the catalog ('jdk'). Deliberately independent of
    // the JDK that runs Gradle and compiles buildSrc (see
    // gradle/gradle-daemon-jvm.properties): a legacy toolchain such as 8 can
    // be paired with a modern daemon. Offline builds need that JDK installed
    // and discoverable (org.gradle.java.installations.paths).
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    // javac defaults to the platform encoding (Cp1252 on Windows)
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
}

dependencies {
    // Test stack from the catalog when the module declares it (subproject
    // templates merge these entries); a module without tests needs nothing.
    libs.findLibrary("junit-jupiter").ifPresent { testImplementation(it) }
    libs.findLibrary("junit-platform-launcher").ifPresent { testRuntimeOnly(it) }
    libs.findLibrary("assertj-core").ifPresent { testImplementation(it) }
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
// Reproducible archive layout: no wall-clock file timestamps, deterministic
// entry order. Two builds of the same commit then differ only in the manifest
// Build-Time attribute (kept deliberately: it has operational value).
// ============================================================================
tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

// ============================================================================
// Git Information (optional, enable with -PenableGitInfo=true)
// Configuration Cache compatible using the Provider API.
// Every exec ignores the exit value so a build outside a Git checkout (export,
// source archive) still works and reports "unknown".
// Full 40-character SHA, deliberately not --short: prefix uniqueness is a
// property of the repository at RESOLUTION time, not at build time - a
// 7-character prefix that is unique today can become ambiguous in a grown
// repository years later, exactly when an audit needs to resolve it. The full
// hash never does. Humans shorten on reading.
// ============================================================================
val enableGitInfo: Provider<Boolean> = providers
    .gradleProperty("enableGitInfo")
    .map { it.toBoolean() }
    .orElse(false)

val gitCommit: Provider<String> = providers.exec {
    commandLine("git", "rev-parse", "HEAD")
    isIgnoreExitValue = true
}.standardOutput.asText.map { it.trim().ifEmpty { "unknown" } }

val gitBranch: Provider<String> = providers.exec {
    commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
    isIgnoreExitValue = true
}.standardOutput.asText.map { it.trim().ifEmpty { "unknown" } }

val gitTag: Provider<String> = providers.exec {
    commandLine("git", "describe", "--tags", "--exact-match")
    isIgnoreExitValue = true
}.standardOutput.asText.map { it.trim().ifEmpty { "none" } }

val gitDirty: Provider<String> = providers.exec {
    commandLine("git", "status", "--porcelain")
    isIgnoreExitValue = true
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
