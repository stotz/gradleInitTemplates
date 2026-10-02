---
name: Java Library
description: Pure Java library module (java-library plugin, no Kotlin) with JUnit 6 + AssertJ
version: 1.0.0
tags: [java, gradle, library, simple]

help: |
  Creates a pure Java library - no Kotlin plugin, no application entry point.
  Intended for Java-only modules in a multiproject (e.g. generated stubs,
  legacy code being migrated) and for standalone Java libraries.

  Features:
    - java-library plugin with api/implementation separation
    - JDK toolchain from the version catalog (independent of the daemon JDK)
    - UTF-8 source encoding, reproducible archives, Git info in the manifest
    - JUnit 6 + AssertJ (disable Kover/SBOM via --config enable_kover=false, enable_sbom=false)

  Usage:
    gradleInit init myLib --template java-library
    gradleInit subproject --template java-library core      # inside a multiproject

  Build:
    ./gradlew build

requirements:
  gradle: ">=9.0"
  jdk: ">=17"

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

  - name: version
    type: string
    help: Project version
    context_key: version
    default: "1.0.0"
    required: false

  - name: enable_kover
    type: boolean
    help: Apply the Kover coverage plugin (reports and the ratchet gate)
    context_key: enable_kover
    default: true
    required: false

  - name: enable_sbom
    type: boolean
    help: Apply the CycloneDX plugin for on-demand SBOM generation
    context_key: enable_sbom
    default: true
    required: false
---

# Java Library Template

A pure Java library module: `java-library` plugin, no Kotlin. The JDK toolchain
comes from `gradle/libs.versions.toml` (`jdk`) and is independent of the JDK that
runs Gradle (`gradle/gradle-daemon-jvm.properties`), so a legacy toolchain can be
paired with a modern daemon - set `jdk` in the catalog after generation.

## Features

- **java-library** - `api` vs `implementation` separation for consumers
- **Version Catalog** - single source of versions
- **UTF-8** - explicit source encoding (javac defaults to the platform encoding)
- **JUnit 6 + AssertJ** - test stack; Kover gate and CycloneDX SBOM optional

## Build

```bash
./gradlew build
./gradlew test -PverboseTests=true
```
