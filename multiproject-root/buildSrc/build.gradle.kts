plugins {
    `kotlin-dsl`
}

// No toolchain pin on purpose: buildSrc is build code and compiles with the
// JDK that runs Gradle (gradle/gradle-daemon-jvm.properties). The project
// toolchain ('jdk' in gradle/libs.versions.toml) applies to the modules only,
// so a legacy toolchain never breaks the build logic.

dependencies {
    implementation(libs.plugins.kotlin.jvm.get().let { 
        "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" 
    })
}
