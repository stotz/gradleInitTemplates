plugins {
    // Plugin declarations would go here if needed
}

rootProject.name = "{{ project_name }}"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }
    
    versionCatalogs {
        create("libs") {
            // Use layout.projectDirectory.file() instead of files() for Gradle 9.x compatibility
            from(layout.projectDirectory.file("gradle/libs.versions.toml"))
        }
    }
}
