# {{ project_name }}

A Kotlin application generated with gradleInit.

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
./gradlew run
```

### Using Java JAR

Build the JAR first:

```shell
./gradlew jar
```

Then run:

```shell
java -cp build/libs/{{ project_name }}-{{ version }}.jar {{ group }}.MainKt
```

## Project Structure

```
src/
  main/
    kotlin/       - Application source code
  test/
    kotlin/       - Test source code
gradle/
  libs.versions.toml  - Version catalog
```

## License

{{ vendor }} - {{ version }}
