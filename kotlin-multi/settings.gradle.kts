// Repositories: defaults are Maven Central / Plugin Portal; set repository_url
// and plugin_repository_url (--config) for an internal mirror or offline repo.
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
    repositories {
{% if repository_url %}
        maven { url = uri("{{ repository_url }}") }
{% else %}
        mavenCentral()
{% endif %}
    }
}

include("app")
include("lib")
