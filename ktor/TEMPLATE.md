---
name: Ktor Application
description: Ktor server application with Kotlin
version: 1.1.0
tags: [kotlin, ktor, rest, api, server]

help: |
  Creates a Ktor web server application with modern async Kotlin.
  
  Features:
    - Ktor 3.x with Netty server
    - Content negotiation (JSON via kotlinx.serialization)
    - YAML configuration support
    - Logback logging
    - Git info in JAR manifest (optional)
  
  Usage:
    gradleInit init myApi --template ktor
    gradleInit init myApi --template ktor --group com.mycompany
  
  Endpoints:
    GET /                  Welcome message (JSON)
    GET /hello?name=World  Personalized greeting
  
  Build & Run:
    ./gradlew build                          # Build project
    ./gradlew build -PenableGitInfo=true     # Build with Git info
    ./gradlew run                            # Start server (port 8080)
    ./gradlew buildFatJar                    # Create fat JAR
    java -jar build/libs/*-all.jar           # Run fat JAR

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
---

# Ktor Application

Ktor server application with Kotlin.

## Features

- Ktor 3.x
- Content negotiation (JSON)
- Optional features: auth, websockets, serialization
- Logback configuration
- Kotlin coroutines

## Usage

```bash
./gradleInit.py init my-service \
  --template ktor \
  --group com.mycompany \
  --config ktor_features=auth,serialization,websockets
```
