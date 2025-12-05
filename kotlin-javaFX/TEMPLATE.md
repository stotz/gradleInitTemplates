---
name: Kotlin JavaFX Project
description: Modern JavaFX application with Kotlin, including Ikonli, ControlsFX, FormsFX, and ValidatorFX
version: 1.0.0
tags: [kotlin, javafx, desktop, gui, ikonli, controlsfx, formsfx, validatorfx]

requirements:
  gradle: ">=8.0"
  kotlin: ">=2.0"
  jdk: ">=21"

raw_copy:
  - dump_src.sh
  - dump_src.cfg

arguments:
  - name: group
    type: string
    help: Maven group ID
    context_key: group
    default: com.example
    required: true
    
  - name: version
    type: string
    help: Project version
    context_key: version
    default: "1.0.0"
    required: false
---

# Kotlin JavaFX Project Template

A modern JavaFX desktop application template with Kotlin, featuring:
- **JavaFX 25** - Modern UI framework
- **Ikonli** - Icon library with FontAwesome 5 support
- **ControlsFX** - Enhanced controls (Notifications, PopOver)
- **FormsFX** - Declarative form creation
- **ValidatorFX** - Input validation framework
- **Beryx JLink** - Native application packaging

## Features

- **Modern Desktop UI** - JavaFX with Kotlin
- **Rich UI Components** - ControlsFX, FormsFX, ValidatorFX
- **Icon Support** - Ikonli with FontAwesome 5
- **Cross-platform** - Automatic platform detection for JavaFX natives
- **JDK 23 Support** - Latest JDK with toolchain configuration
- **Version Catalog** - Centralized dependency management
- **JLink Packaging** - Create native installers
- **JUnit 5** - Modern testing framework

## Project Structure

```
my-javafx-app/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   └── libs.versions.toml
└── src/
    ├── main/
    │   └── kotlin/
    │       └── com/example/
    │           ├── App.kt        # Main JavaFX Application
    │           └── Main.kt       # Entry point
    └── test/
        └── kotlin/
            └── com/example/
                └── AppTest.kt
```

## Getting Started

After project creation:

```bash
cd my-javafx-app

# Build the project
./gradlew build

# Run the application (shows selection screen)
./gradlew run

# Or launch specific demo directly:
./gradlew run --args="--app"        # Simple starter
./gradlew run --args="--advanced"   # Feature showcase

# Run tests
./gradlew test

# Create native package (with JLink)
./gradlew jlink
./gradlew jlinkZip
```

The application includes an **interactive launcher** that lets you choose between:
- **App** - Clean, professional starter (for building your application)
- **AdvancedDemo** - Comprehensive feature showcase (for learning and reference)

## Configuration

The template uses these configuration values:

- `group` - Your Maven group ID (e.g., `com.mycompany`)
- `version` - Project version (default: `1.0.0`)
- `app_name` - Application name for display
- `main_class` - Fully qualified main class name
- `company` - Application company name
- `kotlin_version` - Kotlin version (from gradleInit defaults)
- `jdk_version` - JDK version (default: 23, minimum 21)

### JavaFX Dependencies

The template automatically handles platform-specific JavaFX natives:
- Windows: `win` classifier
- Linux: `linux` classifier
- macOS: `mac` classifier

All JavaFX modules are included:
- javafx-base
- javafx-graphics
- javafx-controls
- javafx-web

### UI Library Versions

- **JavaFX**: 25.0.1
- **Ikonli**: 12.4.0
- **ControlsFX**: 11.2.2
- **FormsFX**: 11.6.0
- **ValidatorFX**: 0.6.3

## Sample Application

The generated project includes a clean starter application demonstrating:

1. **Menu Bar** - File, Edit, Help menus with keyboard shortcuts
2. **Toolbar** - Icon buttons using FontAwesome via Ikonli
3. **Interactive Buttons** - Click events with counter
4. **Clickable Labels** - Custom styled labels with hover effects
5. **Event Handling** - Mouse clicks, hover enter/exit
6. **Status Bar** - Bottom status information display
7. **Toast Notifications** - ControlsFX notifications for user feedback
8. **About Dialog** - Standard JavaFX Alert dialog

**Demo Content:** 
The center area contains demo content showing:
- Click counter button
- Color-coded clickable labels (Red, Green, Blue, Yellow)
- Instructions for customization

**Easy Removal:**
All demo content is in the `createContent()` method. Simply remove it and add your own UI while keeping the BorderPane structure with menu, toolbar, and status bar.

## Next Steps

1. **Customize UI** - Modify `App.kt` to build your interface
2. **Add dependencies** - Edit `gradle/libs.versions.toml`
3. **Configure packaging** - Adjust JLink settings in `build.gradle.kts`
4. **Add resources** - Place images, CSS, FXML in `src/main/resources`

## JLink Packaging

The template includes Beryx JLink plugin for creating native applications:

```bash
# Create runtime image
./gradlew jlink

# Create distributable ZIP
./gradlew jlinkZip

# The runtime image will be in: build/image/
```

The JLink configuration:
- Includes all required modules
- Sets up launcher script
- Configures JVM options
- Supports custom compression levels

## Best Practices

This template follows JavaFX and Gradle best practices:

- Version catalog for centralized dependency management
- Kotlin DSL for type-safe build scripts
- JDK toolchain for consistent Java versions
- Platform-specific JavaFX natives handling
- Proper module configuration for JLink
- Cross-platform compatibility

## Troubleshooting

### JavaFX Runtime Not Found

If you get "JavaFX runtime components are missing":
- Ensure JDK 21+ is used (via toolchain)
- Check platform classifier is correct
- Verify all JavaFX dependencies are included

### JLink Issues

If JLink packaging fails:
- Ensure all dependencies are modular
- Check module configuration
- Verify JDK version compatibility

## License

This template is part of gradleInit and is licensed under MIT.
