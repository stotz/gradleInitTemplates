rootProject.name = "{{ project_name }}"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}

// Subprojects are added here by 'gradleInit subproject' command
// Example: include("api", "core", "ui")
