---
name: Ktor Application
description: Ktor server application with Kotlin
version: 1.0.0
tags: [kotlin, ktor, rest, api]

requirements:
  gradle: ">=8.0"
  kotlin: ">=2.0"
  jdk: ">=17"

raw_copy:
  - dump_src.sh
  - dump_src.cfg
  - gradlew
  - gradlew.bat

subproject_mode:
  skip:
    - gradle/
    - gradlew
    - gradlew.bat
    - settings.gradle.kts
    - .gitignore
    - .editorconfig
    - dump_src.sh
    - dump_src.cfg
    - README.md
  build_file: build.gradle.kts.subproject
  merge_versions: gradle/libs.versions.toml

arguments:
  - name: group
    type: string
    help: Maven group ID
    context_key: group
    default: com.example
    required: true
    
  - name: ktor_features
    type: list
    help: Ktor features to include
    context_key: ktor_features
    default: [serialization]
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
