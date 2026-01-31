# {{ project_name }}

[![Kotlin](https://img.shields.io/badge/Kotlin-{{ kotlin_version }}-7F52FF.svg?logo=kotlin)](https://kotlinlang.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-{{ springboot_version }}-6DB33F.svg?logo=springboot)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A.svg?logo=gradle)](https://gradle.org)
[![JDK](https://img.shields.io/badge/JDK-{{ jdk_version }}-ED8B00.svg?logo=openjdk)](https://openjdk.org)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> A Spring Boot application generated with [gradleInit](https://github.com/anthropics/gradleInit).

---

## 📋 Table of Contents

- [Prerequisites](#-prerequisites)
- [Build](#-build)
- [Test](#-test)
- [Run](#-run)
- [API Endpoints](#-api-endpoints)
- [Configuration](#-configuration)
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
./gradlew bootRun
```

The server starts at http://localhost:8080

### Using Java JAR

Build the executable JAR first:

```shell
./gradlew bootJar
```

Then run:

```shell
java -jar build/libs/{{ project_name }}-{{ version }}.jar
```

## 🌐 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET`  | `/` | Returns a welcome message |
| `GET`  | `/health` | Health check endpoint |

## ⚙️ Configuration

Application configuration is in `src/main/resources/application.properties`.

## 📁 Project Structure

```
src/
  main/
    kotlin/       - Application source code
    resources/    - Configuration files
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
