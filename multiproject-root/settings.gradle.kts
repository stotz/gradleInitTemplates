pluginManagement {
    repositories {
{% if plugin_repository_url %}
        maven { url = uri("{{ plugin_repository_url }}") }
{% else %}
        gradlePluginPortal()
{% endif %}
    }
}

rootProject.name = "{{ project_name }}"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    // Repositories: default Maven Central; set repository_url (--config) for an
    // internal mirror or offline repo (plugins: plugin_repository_url).
    repositories {
{% if repository_url %}
        maven { url = uri("{{ repository_url }}") }
{% else %}
        mavenCentral()
{% endif %}
    }
}

// Subprojects are added here by 'gradleInit subproject' command
// Example: include("api", "core", "ui")
