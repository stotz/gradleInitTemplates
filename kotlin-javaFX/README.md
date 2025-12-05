# {{ app_name }}

A modern JavaFX desktop application built with Kotlin.

## Project Information

- **Group**: {{ group }}
- **Version**: {{ version }}
- **Kotlin**: {{ kotlin_version }}
- **JDK**: {{ jdk_version }}
- **Company**: {{ company }}

## Features

- **JavaFX 25** - Modern desktop UI framework
- **Ikonli** - Icon library with FontAwesome 5
- **ControlsFX** - Enhanced controls (Notifications, PopOver)
- **FormsFX** - Declarative form creation
- **ValidatorFX** - Input validation
- **JLink** - Native packaging support

## Quick Start

### Prerequisites

- JDK {{ jdk_version }} or later
- Gradle (wrapper included)

### Build and Run

```shell
# Build the project
./gradlew clean build

# Run the application
./gradlew run

# Run tests
./gradlew clean test --rerun-tasks

# Run tests with verbose output
./gradlew clean test --rerun-tasks -PverboseTests=true
```

### Create Native Package

```bash
# Create JLink runtime image
./gradlew jlink

# Create distributable ZIP
./gradlew jlinkZip

# Runtime image location: build/image/
```

## Project Structure

```
{{ project_name }}/
├── build.gradle.kts           # Build configuration
├── settings.gradle.kts        # Project settings
├── gradle.properties          # Gradle properties
├── gradle/
│   └── libs.versions.toml    # Version catalog
├── src/
│   ├── main/
│   │   └── kotlin/           # Application source code
│   └── test/
│       └── kotlin/           # Test source code
└── README.md                 # This file
```

## Dependencies

### JavaFX Modules
- javafx-base
- javafx-graphics
- javafx-controls
- javafx-web

### UI Libraries
- **Ikonli** (12.4.0) - Icon support
- **ControlsFX** (11.2.2) - Enhanced controls
- **FormsFX** (11.6.0) - Form framework
- **ValidatorFX** (0.6.3) - Validation

### Build Tools
- **Beryx JLink** (3.1.3) - Native packaging

## Development

### IDE Setup

**IntelliJ IDEA** (Recommended)
```bash
# Import as Gradle project
File -> Open -> Select build.gradle.kts
```

**Eclipse**
```bash
# Generate Eclipse files
./gradlew eclipse
```

**VS Code**
- Install "Kotlin" extension
- Install "Gradle for Java" extension

### Adding Dependencies

Edit `gradle/libs.versions.toml`:

```toml
[versions]
my-library = "1.0.0"

[libraries]
my-library = { module = "com.example:my-library", version.ref = "my-library" }
```

Then use in `build.gradle.kts`:
```kotlin
dependencies {
    implementation(libs.my.library)
}
```

## Configuration

### JDK Version

Modify in `gradle/libs.versions.toml`:
```toml
[versions]
jdk = "23"  # Change to 21, 23, etc.
```

### Application Properties

Edit `build.gradle.kts`:
```kotlin
application {
    mainClass.set("{{ group }}.MainKt")
}
```

### JLink Configuration

Customize in `build.gradle.kts` `jlink` block for:
- Module selection
- Compression level
- Launcher options
- JVM arguments

## Testing

The project uses JUnit 5 for testing.

### Run Tests
```bash
./gradlew test
```

### Test Report
```bash
./gradlew test --info
# Report: build/reports/tests/test/index.html
```

### Add Test Dependencies
```kotlin
dependencies {
    testImplementation("org.testfx:testfx-core:4.0.18")
    testImplementation("org.testfx:testfx-junit5:4.0.18")
}
```

## Troubleshooting

### JavaFX Runtime Not Found

**Solution**: Ensure JDK {{ jdk_version }}+ is configured:
```bash
./gradlew --version  # Check Java version
```

### JLink Fails

**Solution**: Verify all dependencies are modular:
```bash
./gradlew dependencies --configuration runtimeClasspath
```

### Build Fails

**Solution**: Clean and rebuild:
```bash
./gradlew clean build --refresh-dependencies
```

## Resources

- [JavaFX Documentation](https://openjfx.io/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Gradle Documentation](https://docs.gradle.org/)
- [Ikonli](https://kordamp.org/ikonli/)
- [ControlsFX](https://controlsfx.github.io/)
- [FormsFX](https://github.com/dlemmermann/FormsFX)
- [ValidatorFX](https://github.com/effad/ValidatorFX)

## License

{{ company }}
