# {{ project_name }}

A Kotlin multi-module application generated with gradleInit.

## Prerequisites

- JDK {{ jdk_version }} or higher
- Gradle 9.x (wrapper included)

## Build

```shell
./gradlew clean build
```

Build with Git information in JAR manifest:

```shell
./gradlew clean build -PenableGitInfo=true
```

## Test

```shell
./gradlew clean test --rerun-tasks
```

Verbose test output:

```shell
./gradlew clean test --rerun-tasks -PverboseTests=true
```

## Run

### Using Gradle

```shell
./gradlew :app:run
```

### Using Java JAR

Build the distribution first:

```shell
./gradlew :app:installDist
```

Then run:

```shell
./app/build/install/app/bin/app
```

## Project Structure

```
app/                  - Main application module
  src/
    main/kotlin/      - Application source code
    test/kotlin/      - Test source code
lib/                  - Library module
  src/
    main/kotlin/      - Library source code
    test/kotlin/      - Test source code
buildSrc/             - Shared build conventions
gradle/
  libs.versions.toml  - Version catalog
```

## Modules

- **app** - Main application entry point
- **lib** - Shared library code

## Push to GitHub

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

> **Tip:** SSH Setup: https://docs.github.com/en/authentication/connecting-to-github-with-ssh

Verify committed files:

```shell
git status        # Should be clean
git log --oneline # Should show initial commit
git ls-files      # Show all tracked files
```

## License

{{ company }} - {{ version }}
