plugins {
    `kotlin-dsl`
}

// Kotlin 2.x supports max JDK 24 - cap at 24 if system JDK is newer
kotlin {
    jvmToolchain(minOf(libs.versions.jdk.get().toInt(), 24))
}

dependencies {
    implementation(libs.plugins.kotlin.jvm.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
    implementation(libs.plugins.shadow.get().let {
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}"
    })
}
