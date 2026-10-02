// ============================================================================
// Kotlin conventions: everything from java-common-conventions (toolchain,
// encoding, tests, manifest, reproducible archives) plus the Kotlin plugin.
// ============================================================================
plugins {
    id("java-common-conventions")
    kotlin("jvm")
}

// Access version catalog from main project
val libs = the<VersionCatalogsExtension>().named("libs")
val jdkVersion = libs.findVersion("jdk").get().toString().toInt()

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

kotlin {
    // Same toolchain as the Java base (Kotlin 2.3+ supports up to JDK 25 bytecode)
    jvmToolchain(jdkVersion)
}
