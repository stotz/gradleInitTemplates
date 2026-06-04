---
name: Kotlin Multi-Module Project
description: Multi-module Kotlin project with convention plugins
version: 1.1.0
tags: [kotlin, gradle, multi-module, buildSrc]

help: |
  Creates a multi-module Kotlin project with shared convention plugins.
  
  Features:
    - Convention plugins in buildSrc for shared configuration
    - App module (application) + Lib module (library)
    - Centralized version catalog
    - Git info in JAR manifest (optional)
  
  Usage:
    gradleInit init myProject --template kotlin-multi
    gradleInit init myProject --template kotlin-multi --group com.mycompany
  
  Structure:
    myProject/
      buildSrc/           # Convention plugins
      app/                # Application module
      lib/                # Library module
      gradle/libs.versions.toml
  
  Build & Run:
    ./gradlew build                          # Build all modules
    ./gradlew build -PenableGitInfo=true     # Build with Git info
    ./gradlew :app:run                       # Run application

requirements:
  gradle: ">=9.0"
  kotlin: ">=2.0"
  jdk: ">=24"

arguments:
  - name: group
    type: string
    help: Maven group ID (e.g. com.mycompany)
    context_key: group
    default: com.example
    required: false
---

# Kotlin Multi-Module Project

Multi-module project with convention plugins in buildSrc.

## Structure

```
my-project/
├── buildSrc/                 # Convention plugins
│   └── src/main/kotlin/
│       ├── kotlin-common-conventions.gradle.kts
│       └── kotlin-application-conventions.gradle.kts
├── app/                      # Application module
│   └── src/main/kotlin/
├── lib/                      # Library module
│   └── src/main/kotlin/
└── settings.gradle.kts
```

## Features

- Convention plugins for shared configuration
- Modular architecture
- Version catalog
- JDK toolchain
