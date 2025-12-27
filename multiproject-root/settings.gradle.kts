rootProject.name = "{{ project_name }}"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

// Version catalog "libs" is auto-discovered from gradle/libs.versions.toml

// Subprojects are added here by 'gradleInit subproject' command
// Examples:
//   gradleInit subproject api --template ktor
//   gradleInit subproject core --template kotlin-single
//   gradleInit subproject ui --template kotlin-javaFX
