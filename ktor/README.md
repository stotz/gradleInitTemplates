# {{ project_name }}

A Ktor web application generated with gradleInit.

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

The server starts at http://localhost:8080

### Using Java JAR

Build the fat JAR first:

```shell
./gradlew buildFatJar
```

Then run:

```shell
java -jar build/libs/{{ project_name }}-all.jar
```

## API Endpoints

- `GET /` - Returns a welcome message
- `GET /health` - Health check endpoint

## Configuration

Server configuration is in `src/main/resources/application.yaml`.

## Project Structure

```
src/
  main/
    kotlin/       - Application source code
    resources/    - Configuration files (application.yaml)
  test/
    kotlin/       - Test source code
```

## License

{{ vendor }} - {{ version }}
