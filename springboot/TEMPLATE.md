---
name: Spring Boot Application
description: Spring Boot REST API with Kotlin
version: 1.0.0
tags: [kotlin, springboot, rest, api]

requirements:
  gradle: ">=9.0"
  kotlin: ">=2.0"
  jdk: ">=21"

arguments:
  - name: group
    type: string
    help: Maven group ID
    context_key: group
    default: com.example
    required: true
    
  - name: spring_modules
    type: list
    help: Spring Boot modules to include
    context_key: spring_modules
    default: [web]
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
