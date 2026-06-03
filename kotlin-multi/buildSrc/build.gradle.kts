plugins {
    `kotlin-dsl`
}

// Toolchain uses the selected JDK (Kotlin 2.3+ supports up to JDK 25 bytecode)
kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    implementation(libs.plugins.kotlin.jvm.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
    implementation(libs.plugins.shadow.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
}
