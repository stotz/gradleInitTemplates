---
name: Kotlin Single Project
description: Simple single-module Kotlin project with Gradle Kotlin DSL
version: 1.0.0
tags: [kotlin, gradle, simple, single-module]

requirements:
  gradle: ">=8.0"
  kotlin: ">=2.0"
  jdk: ">=17"

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

# Kotlin Single Project Template

A simple single-module Kotlin project with modern Gradle Kotlin DSL configuration.

## Features

- **Gradle Kotlin DSL** - Modern build configuration
- **Version Catalog** - Centralized dependency management via `gradle/libs.versions.toml`
- **JDK Toolchain** - Ensures consistent Java version across environments
- **JUnit 5** - Modern testing framework
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

# Run tests
./gradlew test
```

## Configuration

The template uses these configuration values:

- `group` - Your Maven group ID (e.g., `com.mycompany`)
- `version` - Project version (default: `1.0.0`)
- `kotlin_version` - Kotlin version (from gradleInit defaults)
- `gradle_version` - Gradle version (from gradleInit defaults)
- `jdk_version` - JDK version (default: 21)

### Custom Configuration

You can customize the generated project via `~/.gradleInit`:

```toml
[defaults]
group = "com.mycompany"
version = "1.0.0"
jdk_version = 21

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
