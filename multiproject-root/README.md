# {{ project_name }}

[![Kotlin](https://img.shields.io/badge/Kotlin-{{ kotlin_version }}-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A.svg?logo=gradle)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-{{ jdk_version }}-ED8B00.svg?logo=openjdk)](https://openjdk.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> A multi-module Kotlin project generated with [gradleInit](https://github.com/anthropics/gradleInit).

---

## 📋 Table of Contents

- [Prerequisites](#-prerequisites)
- [Build](#-build)
- [Test](#-test)
- [Project Structure](#-project-structure)
- [Adding Subprojects](#-adding-subprojects)
- [Convention Plugins](#-convention-plugins)
- [Push to GitHub](#-push-to-github)
- [Resources](#-resources)
- [Version](#-version)

---

## 🔧 Prerequisites

- JDK {{ jdk_version }} or higher
- Gradle (wrapper included)

## 🏗️ Build

```shell
./gradlew clean build
```

Build with Git information in JAR manifest:

```shell
./gradlew clean build -PenableGitInfo=true
```

## 🧪 Test

```shell
./gradlew clean test --rerun-tasks
```

Verbose test output:

```shell
./gradlew clean test --rerun-tasks -PverboseTests=true
```

## 📁 Project Structure

```
{{ project_name }}/
  settings.gradle.kts      # Project settings
  gradle.properties        # Shared properties (JDK version)
  gradle/
    libs.versions.toml     # Centralized version catalog
  buildSrc/                # Convention plugins
    src/main/kotlin/
      kotlin-common-conventions.gradle.kts
```

## ➕ Adding Subprojects

Use gradleInit to add subprojects:

```shell
gradleInit subproject api --template ktor
gradleInit subproject core --template kotlin-single
gradleInit subproject ui --template kotlin-javaFX
```

Each subproject will:
- Be created in its own directory
- Use shared conventions from buildSrc
- Have dependencies merged into libs.versions.toml
- Be added to settings.gradle.kts

## 🔌 Convention Plugins

All subprojects should apply the common conventions:

```kotlin
plugins {
    id("kotlin-common-conventions")
}
```

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

## 📚 Resources

- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Gradle Documentation](https://docs.gradle.org/)
- [Gradle Multi-Project Builds](https://docs.gradle.org/current/userguide/multi_project_builds.html)
- [Convention Plugins](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html)
- [Version Catalogs](https://docs.gradle.org/current/userguide/platforms.html)

---

## 📄 Version

{{ group }}:{{ project_name }}:{{ version }}
