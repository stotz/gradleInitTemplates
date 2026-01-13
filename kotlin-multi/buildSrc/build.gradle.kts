plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:{{ kotlin_version }}")
    implementation("com.gradleup.shadow:shadow-gradle-plugin:9.3.1")
}
