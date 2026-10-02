import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

plugins {
    `java-library`
{% if enable_kover %}
    alias(libs.plugins.kover)
{% endif %}
{% if enable_sbom %}
    alias(libs.plugins.cyclonedx.bom)
{% endif %}
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Library version (e.g. 1.0.0)=1.0.0@@version }}"

repositories {
{% if repository_url %}
    maven { url = uri("{{ repository_url }}") }
{% else %}
    mavenCentral()
{% endif %}
}

java {
    // Project toolchain from the catalog ('jdk'). Independent of the JDK that
    // runs Gradle (gradle/gradle-daemon-jvm.properties): a legacy toolchain such
    // as 8 can be paired with a modern daemon. Offline builds need that JDK
    // installed and discoverable (org.gradle.java.installations.paths).
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jdk.get().toInt()))
    }
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    // javac defaults to the platform encoding (Cp1252 on Windows)
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
    testRuntimeOnly(libs.junit.platform.launcher)
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
// Configuration Cache compatible using the Provider API. Every exec ignores
// the exit value so a build outside a Git checkout still works. Full
// 40-character SHA on purpose: prefix uniqueness is a property of the
// repository at resolution time, not at build time.
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

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to "{{ project_name }}",
            "Implementation-Version" to version.toString(),
            "Implementation-Vendor" to "{{ group }}"
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

{% if enable_kover %}
// ============================================================================
// Coverage gate (Kover). The verification rule is a ratchet: the 50 percent
// bound is a deliberately conservative starting floor, not the ambition -
// raise it toward the measured value after each coverage run, so the gate can
// only ever tighten. The filter excludes are the project-specific part:
// exclude code whose execution coverage lives outside unit tests (generated
// stubs, integration-only code), because measuring it in a unit-only run
// would only produce noise.
// koverVerify runs after every `test` invocation; koverHtmlReport writes
// build/reports/kover/html.
// ============================================================================
kover {
    reports {
        verify {
            rule("line coverage of unit-testable logic") {
                minBound(50)
            }
        }
    }
}

tasks.test {
    finalizedBy(tasks.named("koverVerify"))
}
{% endif %}
{% if enable_sbom %}
// ============================================================================
// SBOM (CycloneDX): `./gradlew cyclonedxBom` writes build/reports/cyclonedx/bom.{json,xml}.
// The jar manifest answers "which of OUR code runs"; the SBOM answers "which
// dependencies in which versions" - machine-readable for CVE scanning and
// license review. Generated on demand, not on every build.
// Scoped to the runtime classpath, deliberately: test frameworks and build
// agents never ship. An SBOM must answer "what runs in production".
// ============================================================================
tasks.cyclonedxDirectBom {
    projectType = org.cyclonedx.model.Component.Type.LIBRARY
    includeConfigs = listOf("runtimeClasspath")
}
{% endif %}
