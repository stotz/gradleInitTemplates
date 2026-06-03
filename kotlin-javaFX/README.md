# {{ app_name | default(project_name) }}

[![Kotlin](https://img.shields.io/badge/Kotlin-{{ kotlin_version }}-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![JavaFX](https://img.shields.io/badge/JavaFX-{{ javafx_version }}-007396.svg?logo=java)](https://openjfx.io)
[![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A.svg?logo=gradle)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-{{ jdk_version }}-ED8B00.svg?logo=openjdk)](https://openjdk.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> A modern JavaFX desktop application built with Kotlin, generated with [gradleInit](https://github.com/anthropics/gradleInit).

---

## 📋 Table of Contents

- [Project Information](#-project-information)
- [Features](#-features)
- [Quick Start](#-quick-start)
- [Project Structure](#-project-structure)
- [Dependencies](#-dependencies)
- [Development](#-development)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Troubleshooting](#-troubleshooting)
- [Push to GitHub](#-push-to-github)
- [Resources](#-resources)
- [License](#-license)

---

## 📊 Project Information

- **Group**: {{ group }}
- **Version**: {{ version }}
- **Kotlin**: {{ kotlin_version }}
- **JDK**: {{ jdk_version }}
- **Vendor**: {{ vendor }}

## ✨ Features

- **JavaFX 25** - Modern desktop UI framework
- **Ikonli** - Icon library with FontAwesome 5
- **ControlsFX** - Enhanced controls (Notifications, PopOver)
- **FormsFX** - Declarative form creation
- **ValidatorFX** - Input validation
- **JLink** - Native packaging support

## 🚀 Quick Start

### Prerequisites

- JDK {{ jdk_version }} or later
- Gradle (wrapper included)

### Build and Run

```bash
# Build the project
./gradlew build

# Build with Git information in JAR manifest
./gradlew build -PenableGitInfo=true

# Run the application
./gradlew run

# Run tests
./gradlew test
```

### Create Native Package

```bash
# Create JLink runtime image
./gradlew jlink

# Create distributable ZIP
./gradlew jlinkZip

# Runtime image location: build/image/
```

## 📁 Project Structure

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

## 📦 Dependencies

### JavaFX Modules
- javafx-base
- javafx-graphics
- javafx-controls
- javafx-web

<!-- vregion:begin -->
### UI Libraries
- **Ikonli** (<!--v:ikonli-->12.4.0<!--/v-->) - Icon support
- **ControlsFX** (<!--v:controlsfx-->11.2.3<!--/v-->) - Enhanced controls
- **FormsFX** (<!--v:formsfx-->11.6.0<!--/v-->) - Form framework
- **ValidatorFX** (<!--v:validatorfx-->0.6.3<!--/v-->) - Validation

### Build Tools
- **Beryx JLink** (<!--v:beryx_jlink-->3.1.3<!--/v-->) - Native packaging
<!-- vregion:end -->

## 💻 Development

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

## ⚙️ Configuration

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

## 🧪 Testing

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

## 🔧 Troubleshooting

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

## 📚 Resources

- [JavaFX Documentation](https://openjfx.io/)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Gradle Documentation](https://docs.gradle.org/)
- [Ikonli](https://kordamp.org/ikonli/)
- [ControlsFX](https://controlsfx.github.io/)
- [FormsFX](https://github.com/dlemmermann/FormsFX)
- [ValidatorFX](https://github.com/effad/ValidatorFX)

---

## 🚀 Push to GitHub

Your project is ready to push to GitHub!

### Option 1: Create new repository on GitHub

1. Go to https://github.com/new
2. Repository name: `{{ project_name }}`
3. **DO NOT** initialize with README, .gitignore, or license
4. Click 'Create repository'
5. Then run:

```shell
cd {{ project_name }}

# Using HTTPS (easier, requires username/password or token)
git remote add origin https://github.com/YOUR_USERNAME/{{ project_name }}.git
git branch -M main
git push -u origin main

# OR using SSH (recommended, requires SSH key setup)
git remote add origin git@github.com:YOUR_USERNAME/{{ project_name }}.git
git branch -M main
git push -u origin main
```

### Option 2: Using GitHub CLI (recommended)

```shell
cd {{ project_name }}
gh repo create {{ project_name }} --public --source=. --push
```

### Option 3: Push to existing repository

```shell
cd {{ project_name }}

# HTTPS:
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git

# OR SSH:
git remote add origin git@github.com:YOUR_USERNAME/YOUR_REPO.git

git branch -M main
git push -u origin main
```

> **💡 Tip:** SSH Setup: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

Verify committed files:

```shell
git status        # Should be clean
git log --oneline # Should show initial commit
git ls-files      # Show all tracked files
```

---

## 📄 License

{{ vendor }}
