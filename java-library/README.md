# {{ project_name }}

Pure Java library built with Gradle (Kotlin DSL).

## Build

```bash
./gradlew build                       # compile, test, jar
./gradlew test -PverboseTests=true    # verbose test output
./gradlew build -PenableGitInfo=true  # Git info in the jar manifest
```

## Toolchain

The project compiles with the JDK declared as `jdk` in `gradle/libs.versions.toml`.
The JDK that runs Gradle is declared separately in `gradle/gradle-daemon-jvm.properties`.
