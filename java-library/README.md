# {{ project_name }}

Pure Java library built with Gradle (Kotlin DSL).

## Legacy toolchain (Java 8 / 11)

The project toolchain is `jdk` in `gradle/libs.versions.toml`; the JDK that runs Gradle
is independent (`gradle/gradle-daemon-jvm.properties`, default 25). For a legacy module:

```toml
jdk = "8"
# JUnit 6 requires Java 17: pin the last Java-8 line (platform is versioned 1.x there)
junit = "5.13.4"
```
```toml
junit-platform-launcher = { module = "org.junit.platform:junit-platform-launcher", version = "1.13.4" }
```

Make the JDK discoverable in `gradle.properties`
(`org.gradle.java.installations.fromEnv=JAVA_HOME_8_X64`) and set that variable on each
machine; CI platforms set `JAVA_HOME_<major>_X64` automatically. Verify with
`./gradlew -q javaToolchains`. The pin falls away when the toolchain moves to 17+.

## Build

```bash
./gradlew build                       # compile, test, jar
./gradlew test -PverboseTests=true    # verbose test output
./gradlew build -PenableGitInfo=true  # Git info in the jar manifest
```

## Toolchain

The project compiles with the JDK declared as `jdk` in `gradle/libs.versions.toml`.
The JDK that runs Gradle is declared separately in `gradle/gradle-daemon-jvm.properties`.
