import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm")
}

group = "{{ @@01|Maven group ID (e.g. com.company)=com.example@@group }}"
version = "{{ @@02|Project version=1.0.0@@version }}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

val jdkVersion = providers.gradleProperty("jdkVersion").get()
kotlin {
    jvmToolchain(jdkVersion.toInt())
}

val verboseTests = providers
    .gradleProperty("verboseTests")
    .map { it.toBoolean() }
    .orElse(false)

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("FAILED", "SKIPPED")
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

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
