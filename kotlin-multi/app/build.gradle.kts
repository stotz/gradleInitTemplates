plugins {
    id("kotlin-application-conventions")
    alias(libs.plugins.kover)
    alias(libs.plugins.cyclonedx.bom)
}

dependencies {
    implementation(project(":lib"))
}

application {
    mainClass.set("{{ group }}.app.MainKt")
}

// ============================================================================
// Coverage reporting (Kover). No verification gate yet: the app module is
// entry-point wiring around :lib; add the gate once it gains unit-testable
// logic of its own.
// Once real tests exist, add a ratchet:
//     kover { reports { verify { rule("line coverage") { minBound(50) } } } }
//     tasks.test { finalizedBy(tasks.named("koverVerify")) }
// with a conservative floor, and raise it toward the measured value after
// each coverage run. koverHtmlReport writes build/reports/kover/html.
// ============================================================================

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
