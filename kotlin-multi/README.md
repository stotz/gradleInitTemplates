# {{ project_name }}

A Kotlin multi-module application generated with gradleInit.

## Prerequisites

- JDK {{ jdk_version }} or higher
- Gradle 9.x (wrapper included)

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

## Run

### Using Gradle

```shell
./gradlew :app:run
```

### Using Java JAR

Build the distribution first:

```shell
./gradlew :app:installDist
```

Then run:

```shell
./app/build/install/app/bin/app
```

## Project Structure

```
app/                  - Main application module
  src/
    main/kotlin/      - Application source code
    test/kotlin/      - Test source code
lib/                  - Library module
  src/
    main/kotlin/      - Library source code
    test/kotlin/      - Test source code
buildSrc/             - Shared build conventions
gradle/
  libs.versions.toml  - Version catalog
```

## Modules

- **app** - Main application entry point
- **lib** - Shared library code

## License

{{ company }} - {{ version }}
