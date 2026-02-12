---
name: Kotlin Multi-Module Project
description: Multi-module Kotlin project with convention plugins
version: 1.0.0
tags: [kotlin, gradle, multi-module, buildSrc]

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
