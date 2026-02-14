---
name: Spring Boot Application
description: Spring Boot REST API with Kotlin
version: 1.2.0
tags: [kotlin, springboot, rest, api, server]

help: |
  Creates a Spring Boot 4.x REST API application with Kotlin.
  
  Features:
    - Spring Boot 4.0 with Web starter
    - Kotlin compiler options for Spring (-Xjsr305=strict)
    - JUnit 5 testing with Spring Boot Test
    - Git info in JAR manifest (optional)
  
  Usage:
    gradleInit init myApi --template springboot
    gradleInit init myApi --template springboot --group com.mycompany
  
  Endpoints:
    GET /        Welcome message
    GET /health  Health check
  
  Build & Run:
    ./gradlew build                          # Build project
    ./gradlew build -PenableGitInfo=true     # Build with Git info
    ./gradlew bootRun                        # Start server (port 8080)
    ./gradlew bootJar                        # Create executable JAR
    java -jar build/libs/*.jar               # Run JAR

requirements:
  gradle: ">=9.0"
  kotlin: ">=2.0"
  jdk: ">=21"

subproject_mode:
  build_file: build.gradle.kts.subproject
  merge_versions: gradle/libs.versions.toml
  skip:
    - settings.gradle.kts
    - gradle/
    - .gitignore
    - .gitattributes
    - .editorconfig
    - gradle.properties
    - README.md
    - dump_src.sh.raw
    - dump_src.cfg.raw

arguments:
  - name: group
    type: string
    help: Maven group ID (e.g. com.mycompany)
    context_key: group
    default: com.example
    required: false
---

# Spring Boot Application

Spring Boot REST API with Kotlin.

## Features

- Spring Boot 3.x
- REST API (web module)
- Optional modules: data-jpa, security, actuator
- Kotlin coroutines support
- Logback configuration

## Usage

```bash
./gradleInit.py init my-api \
  --template springboot \
  --group com.mycompany \
  --config spring_modules=web,data-jpa,actuator
```
