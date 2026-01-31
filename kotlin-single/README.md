# {{ project_name }}

[![Kotlin](https://img.shields.io/badge/Kotlin-{{ kotlin_version }}-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A.svg?logo=gradle)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-{{ jdk_version }}-ED8B00.svg?logo=openjdk)](https://openjdk.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
{% if enable_clikt %}[![Clikt](https://img.shields.io/badge/Clikt-5.1.0-4285F4.svg)](https://ajalt.github.io/clikt/){% endif %}

> A Kotlin application generated with [gradleInit](https://github.com/anthropics/gradleInit).

---

## 📋 Table of Contents

- [Prerequisites](#-prerequisites)
- [Build](#-build)
- [Test](#-test)
- [Run](#-run)
- [Project Structure](#-project-structure)
- [Push to GitHub](#-push-to-github)

---

## 🔧 Prerequisites

- JDK {{ jdk_version }} or higher
- Gradle 9.x (wrapper included)

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

## 🚀 Run

### Using Gradle

```shell
./gradlew run
```

### Using Fat JAR (recommended)

Build the fat JAR first:

```shell
./gradlew shadowJar
```

Then run:

```shell
java -jar build/libs/{{ project_name }}-{{ version }}-all.jar --help
```
{% if enable_clikt %}

Example commands:

```shell
java -jar build/libs/{{ project_name }}-{{ version }}-all.jar greet -n "World" -c 3
java -jar build/libs/{{ project_name }}-{{ version }}-all.jar info --verbose
```
{% endif %}

## 📁 Project Structure

```
src/
  main/
    kotlin/       - Application source code
  test/
    kotlin/       - Test source code
gradle/
  libs.versions.toml  - Version catalog
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

## 📄 License

{{ company }} - {{ version }}
