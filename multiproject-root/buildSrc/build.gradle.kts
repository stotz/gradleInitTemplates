plugins {
    `kotlin-dsl`
}

dependencies {
    // Kotlin plugin for convention plugins
    // Version should match kotlin version in gradle/libs.versions.toml
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:{{ kotlin_version }}")
}
