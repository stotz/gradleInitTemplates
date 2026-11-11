import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import java.net.InetAddress
import java.time.Instant

plugins {
    java
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.shadow)
    alias(libs.plugins.beryx.jlink)
{% if enable_kover %}
    alias(libs.plugins.kover)
{% endif %}
{% if enable_sbom %}
    alias(libs.plugins.cyclonedx.bom)
{% endif %}
}

group = "{{ @@02|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@03|Application version (e.g. 1.0.0)=1.0.0@@version }}"

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
    testImplementation(libs.junit.jupiter)

}

val jdkVersion = libs.versions.jdk.get().toInt()
kotlin {
    // Toolchain uses the selected JDK (Kotlin 2.3+ supports up to JDK 25 bytecode)
    jvmToolchain(jdkVersion)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(jdkVersion))
    }
}

application {
    // Main.kt lives in the group package (see src/main/kotlin/Main.kt)
    mainClass.set("{{ group }}.MainKt")
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
            "Implementation-Title" to "{{ app_name | default(project_name) }}",
            "Implementation-Version" to version.toString(),
            "Implementation-Vendor" to "{{ @@06|Vendor/Author name=Example Corp@@vendor }}"
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
// Coverage reporting (Kover). No verification gate yet: the template ships only
// smoke tests, and JavaFX UI code needs TestFX-based tests before a line
// coverage bound is meaningful.
// Once real tests exist, add a ratchet:
//     kover { reports { verify { rule("line coverage") { minBound(50) } } } }
//     tasks.test { finalizedBy(tasks.named("koverVerify")) }
// with a conservative floor, and raise it toward the measured value after
// each coverage run. koverHtmlReport writes build/reports/kover/html.
// ============================================================================
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
