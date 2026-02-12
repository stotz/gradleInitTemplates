---
name: Multiproject Root
description: Root project structure for multi-module Gradle builds
version: 1.1.0
tags: [kotlin, gradle, multi-module, root]

help: |
  Creates a root project structure for multi-module Gradle builds.
  
  Features:
    - Convention plugins in buildSrc
    - Centralized version catalog
    - Ready for adding subprojects
    - Git info in JAR manifest (optional)
  
  Usage:
    gradleInit init myProject --template multiproject-root
    gradleInit init myProject --template multiproject-root --group com.mycompany
  
  Adding Subprojects:
    cd myProject
    gradleInit subproject api --template ktor
    gradleInit subproject core --template kotlin-single
    gradleInit subproject ui --template kotlin-javaFX
  
  Structure:
    myProject/
      buildSrc/                # Convention plugins
      gradle/libs.versions.toml
      settings.gradle.kts
  
  Build:
    ./gradlew build                          # Build all modules
    ./gradlew build -PenableGitInfo=true     # Build with Git info

requirements:
  gradle: ">=9.0"
  kotlin: ">=2.0"
  jdk: ">=21"

raw_copy:
  - dump_src.sh
  - dump_src.cfg
  - gradlew
  - gradlew.bat

arguments:
  - name: group
    type: string
    help: Maven group ID (e.g. com.mycompany)
    context_key: group
    default: com.example
    required: false
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
