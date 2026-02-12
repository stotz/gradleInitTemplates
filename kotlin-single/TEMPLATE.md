---
name: Kotlin Single Project
description: Simple single-module Kotlin project with Gradle Kotlin DSL
version: 1.2.0
tags: [kotlin, gradle, simple, single-module, cli]

help: |
  Creates a standalone Kotlin CLI application with modern Gradle setup.
  
  Features:
    - Shadow plugin for fat JAR creation
    - JUnit 5 + AssertJ + MockK testing
    - Git info in JAR manifest (optional)
    - Clikt CLI framework with Markdown help (optional)
  
  Usage:
    gradleInit init myApp --template kotlin-single
    gradleInit init myApp --template kotlin-single --group com.mycompany
    gradleInit init myApp --template kotlin-single --config enable_clikt=true
  
  Build & Run:
    ./gradlew build                          # Build project
    ./gradlew build -PenableGitInfo=true     # Build with Git info
    ./gradlew run                            # Run application
    java -jar build/libs/*-all.jar --help    # Run fat JAR

requirements:
  gradle: ">=9.0"
  kotlin: ">=2.0"
  jdk: ">=21"

arguments:
  - name: group
    type: string
    help: Maven group ID (e.g. com.mycompany)
    context_key: group
    default: com.example
    required: false
    
  - name: version
    type: string
    help: Project version
    context_key: version
    default: "1.0.0"
    required: false

  - name: enable_clikt
    type: boolean
    help: Enable Clikt CLI framework with Markdown help rendering
    context_key: enable_clikt
    default: false
    required: false
---

# Kotlin Single Project Template

A simple single-module Kotlin project with modern Gradle Kotlin DSL configuration.

## Features

- **Gradle Kotlin DSL** - Modern build configuration
- **Version Catalog** - Centralized dependency management via `gradle/libs.versions.toml`
- **JDK Toolchain** - Ensures consistent Java version across environments
- **JUnit 5** - Modern testing framework
- **CLI Support** - Built-in `--help` and `--info` commands
- **Optional Clikt** - Full CLI framework with Markdown help rendering
- **Git Info** - Optional build metadata in JAR manifest
- **EditorConfig** - Consistent code formatting
- **Git ready** - Complete `.gitignore` configuration

## Project Structure

```
my-project/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── .gitignore
├── .editorconfig
├── gradle/
│   └── libs.versions.toml
└── src/
    ├── main/
    │   ├── kotlin/
    │   │   └── com/example/
    │   │       └── Main.kt
    │   └── resources/
    └── test/
        └── kotlin/
            └── com/example/
                └── MainTest.kt
```

## Getting Started

After project creation:

```bash
cd my-project

# Build the project
./gradlew build

# Run the application
./gradlew run

# Run with arguments
./gradlew run --args="--help"
./gradlew run --args="info"
./gradlew run --args="info --verbose"

# Run tests
./gradlew test
```

## CLI Options

The generated application supports:

```bash
# Show help
myapp --help

# Show version and build info
myapp info
myapp info --verbose    # Show all manifest attributes

# With Clikt enabled: additional options
myapp --name "World" --count 3
```

## Build with Git Info

To include Git and build information in the JAR manifest:

```bash
./gradlew jar -PenableGitInfo=true
```

This adds to the manifest:
- Git-Commit, Git-Branch, Git-Tag, Git-Dirty
- Build-Time, Build-OS, Build-Host, Build-Jdk, Built-By

## Configuration

The template uses these configuration values:

- `group` - Your Maven group ID (e.g., `com.mycompany`)
- `version` - Project version (default: `1.0.0`)
- `enable_clikt` - Enable Clikt CLI framework (default: `false`)
- `kotlin_version` - Kotlin version (from gradleInit defaults)
- `gradle_version` - Gradle version (from gradleInit defaults)
- `jdk_version` - JDK version (default: 25)

### Enable Clikt CLI Framework

```bash
gradleInit init myapp --template kotlin-single --config enable_clikt=true
```

This enables:
- Clikt CLI framework with subcommands
- Markdown-rendered help messages
- Option parsing with defaults

### Custom Configuration

You can customize the generated project via `~/.gradleInit`:

```toml
[defaults]
group = "com.mycompany"
version = "1.0.0"
jdk_version = 25

[custom]
author = "Your Name"
company = "Your Company"
```

Or via environment variables:

```bash
export GRADLE_INIT_AUTHOR="Your Name"
export GRADLE_INIT_COMPANY="Your Company"
```

## Next Steps

1. **Add dependencies** - Edit `gradle/libs.versions.toml`
2. **Configure application** - Modify `build.gradle.kts`
3. **Write code** - Start in `src/main/kotlin`
4. **Add tests** - Expand `src/test/kotlin`

## Best Practices

This template follows Gradle best practices:

- Version catalog for centralized dependency management
- Kotlin DSL for type-safe build scripts
- JDK toolchain for consistent Java versions
- Reproducible builds with `gradle.properties`

## License

This template is part of gradleInit and is licensed under MIT.
