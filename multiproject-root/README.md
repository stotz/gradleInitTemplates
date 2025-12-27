# {{ project_name }}

A multi-module Kotlin project generated with gradleInit.

## Prerequisites

- JDK {{ jdk_version }} or higher
- Gradle (wrapper included)

## Build

```shell
./gradlew clean build
```

## Test

```shell
./gradlew clean test --rerun-tasks
```

Verbose test output:

```shell
./gradlew clean test --rerun-tasks -PverboseTests=true
```

## Project Structure

```
{{ project_name }}/
  settings.gradle.kts      # Project settings
  gradle.properties        # Shared properties (JDK version)
  gradle/
    libs.versions.toml     # Centralized version catalog
  buildSrc/                # Convention plugins
    src/main/kotlin/
      kotlin-common-conventions.gradle.kts
```

## Adding Subprojects

Use gradleInit to add subprojects:

```shell
gradleInit subproject api --template ktor
gradleInit subproject core --template kotlin-single
gradleInit subproject ui --template kotlin-javaFX
```

Each subproject will:
- Be created in its own directory
- Use shared conventions from buildSrc
- Have dependencies merged into libs.versions.toml
- Be added to settings.gradle.kts

## Convention Plugins

All subprojects should apply the common conventions:

```kotlin
plugins {
    id("kotlin-common-conventions")
}
```

## Version

{{ group }}:{{ project_name }}:{{ version }}
