import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktor)
{% if enable_kover %}
    alias(libs.plugins.kover)
{% endif %}
{% if enable_sbom %}
    alias(libs.plugins.cyclonedx.bom)
{% endif %}
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Application version (e.g. 1.0.0)=1.0.0@@version }}"

// Project toolchain version from the catalog (used by toolchain and test JVM flags)
val jdkVersion = libs.versions.jdk.get().toInt()

repositories {
{% if repository_url %}
    maven { url = uri("{{ repository_url }}") }
{% else %}
    mavenCentral()
{% endif %}
}

tasks.withType<JavaCompile>().configureEach {
    // javac defaults to the platform encoding (Cp1252 on Windows)
    options.encoding = "UTF-8"
}

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    // Toolchain uses the selected JDK (Kotlin 2.3+ supports up to JDK 25 bytecode)
    jvmToolchain(jdkVersion)
}

dependencies {
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)
    
    testImplementation(libs.ktor.server.test.host)
    testImplementation(kotlin("test"))
}

val verboseTests = providers
    .gradleProperty("verboseTests")
    .map { it.toBoolean() }
    .orElse(false)

tasks.test {
    useJUnitPlatform()

    // mockk uses byte-buddy, which attaches an agent and calls sun.misc.Unsafe on
    // JDK 24+. These flags silence the resulting VM warnings so test output stays
    // clean (harmless for modules without mockk). Guarded by the toolchain version:
    // the flags do not exist on older JVMs and would make the test JVM refuse to
    // start (e.g. a legacy toolchain 8).
    //   -XX:+EnableDynamicAgentLoading         dynamic agent self-attach warning (JDK 21+)
    //   -Xshare:off                            CDS warning (agent appends to the bootstrap classpath)
    //   --sun-misc-unsafe-memory-access=allow  sun.misc.Unsafe deprecation warnings (JDK 23+)
    if (jdkVersion >= 21) {
        jvmArgs("-XX:+EnableDynamicAgentLoading", "-Xshare:off")
    }
    if (jdkVersion >= 23) {
        jvmArgs("--sun-misc-unsafe-memory-access=allow")
    }
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
// exclude code whose execution coverage lives outside unit tests (env-gated
// integration tests, live operations, manual tooling, entry-point wiring),
// because measuring it in a unit-only run would only produce noise. Extend
// the excludes as the project grows.
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
// Scoped to the runtime classpath, deliberately: the zero-config default
// aggregates every resolvable configuration (test frameworks, the Kover agent,
// embedded compilers), none of which ships in production. An SBOM must answer
// "what runs in production".
// ============================================================================
tasks.cyclonedxDirectBom {
    projectType = org.cyclonedx.model.Component.Type.APPLICATION
    includeConfigs = listOf("runtimeClasspath")
}
{% endif %}
