---
name: Multiproject Root
description: Root project structure for multi-module Gradle builds
version: 1.0.0
tags: [kotlin, gradle, multi-module, root]

requirements:
  gradle: ">=8.0"
  kotlin: ">=2.0"
  jdk: ">=17"

raw_copy:
  - dump_src.sh
  - dump_src.cfg
  - gradlew
  - gradlew.bat

arguments:
  - name: group
    type: string
    help: Maven group ID
    context_key: group
    default: com.example
    required: true
---

# Multiproject Root Template

Root project structure for multi-module Gradle builds with convention plugins.

## Structure

```
myproject/
  settings.gradle.kts      # Project settings with version catalog
  gradle.properties        # Shared properties
  gradle/
    libs.versions.toml     # Centralized version catalog
  buildSrc/
    build.gradle.kts       # Convention plugins build
    settings.gradle.kts
    src/main/kotlin/
      kotlin-common-conventions.gradle.kts
  .gitignore
  .editorconfig
  README.md
```

## Usage

After creating the root project, add subprojects:

```bash
gradleInit init myproject --template multiproject-root
cd myproject
gradleInit subproject api --template ktor
gradleInit subproject core --template kotlin-single
gradleInit subproject ui --template kotlin-javaFX
```

## Convention Plugins

The buildSrc provides shared conventions:
- `kotlin-common-conventions` - Base Kotlin configuration, testing, JDK toolchain
