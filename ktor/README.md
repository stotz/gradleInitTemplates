# {{ project_name }}

A Ktor web application generated with gradleInit.

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
./gradlew run
```

The server starts at http://localhost:8080

### Using Fat JAR

Build the fat JAR first:

```shell
./gradlew buildFatJar
```

Then run:

```shell
java -jar build/libs/{{ project_name }}-all.jar
```

## API Endpoints

- `GET /` - Returns a JSON welcome message
- `GET /hello?name=YourName` - Returns a personalized greeting

## Project Structure

```
src/
  main/
    kotlin/       - Application source code
    resources/    - Configuration files (logback.xml)
  test/
    kotlin/       - Test source code
```

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

## Version

{{ group }}:{{ project_name }}:{{ version }}
