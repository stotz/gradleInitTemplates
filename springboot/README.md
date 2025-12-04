# {{ project_name }}

A Spring Boot application generated with gradleInit.

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
./gradlew bootRun
```

The server starts at http://localhost:8080

### Using Java JAR

Build the executable JAR first:

```shell
./gradlew bootJar
```

Then run:

```shell
java -jar build/libs/{{ project_name }}-{{ version }}.jar
```

## API Endpoints

- `GET /` - Returns a welcome message
- `GET /health` - Health check endpoint

## Configuration

Application configuration is in `src/main/resources/application.properties`.

## Project Structure

```
src/
  main/
    kotlin/       - Application source code
    resources/    - Configuration files
  test/
    kotlin/       - Test source code
gradle/
  libs.versions.toml  - Version catalog
```

## License

{{ vendor }} - {{ version }}
