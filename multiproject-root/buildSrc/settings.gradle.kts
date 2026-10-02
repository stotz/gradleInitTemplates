rootProject.name = "buildSrc"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    // Repositories: default Maven Central + Plugin Portal; set repository_url /
    // plugin_repository_url (--config) for an internal mirror or offline repo.
    repositories {
{% if repository_url %}
        maven { url = uri("{{ repository_url }}") }
{% else %}
        mavenCentral()
{% endif %}
{% if plugin_repository_url %}
        maven { url = uri("{{ plugin_repository_url }}") }
{% else %}
        gradlePluginPortal()
{% endif %}
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
