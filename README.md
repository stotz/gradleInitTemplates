# gradleInitTemplates

Official template repository for [gradleInit](https://github.com/stotz/gradleInit).

## Available Templates

### kotlin-single
Simple single-module Kotlin project with Gradle Kotlin DSL.

**Features:**
- Gradle Kotlin DSL
- Version catalog (libs.versions.toml)
- JDK toolchain configuration
- EditorConfig
- Git ready (.gitignore)
- JUnit 5 tests

**Usage:**
```bash
./gradleInit.py init my-app --template kotlin-single --group com.mycompany
```

### kotlin-multi
Multi-module Kotlin project with convention plugins.

**Features:**
- Multi-module structure
- Convention plugins in buildSrc
- Shared dependencies via version catalog
- Modular architecture
- Composite builds support

**Usage:**
```bash
./gradleInit.py init my-project --template kotlin-multi --group com.mycompany
```

### springboot
Spring Boot REST API with Kotlin.

**Features:**
- Spring Boot 3.x
- REST API starter
- Spring Data JPA (optional)
- Spring Security (optional)
- Actuator (optional)
- Logback configuration

**Usage:**
```bash
./gradleInit.py init my-api \
  --template springboot \
  --group com.mycompany \
  --config spring.modules=web,data-jpa,actuator
```

### ktor
Ktor server application.

**Features:**
- Ktor 3.x
- Content negotiation (JSON)
- Authentication (optional)
- WebSockets (optional)
- Logback configuration

**Usage:**
```bash
./gradleInit.py init my-service \
  --template ktor \
  --group com.mycompany \
  --config ktor.features=auth,serialization,websockets
```

### kotlin-javaFX
Modern JavaFX desktop application with Kotlin.

**Features:**
- JavaFX 25
- Ikonli (icon library with FontAwesome 5)
- ControlsFX (enhanced controls)
- FormsFX (declarative forms)
- ValidatorFX (input validation)
- Beryx JLink (native packaging)
- JDK 23 support
- Cross-platform (Windows, Linux, macOS)

**Usage:**
```bash
./gradleInit.py init my-javafx-app \
  --template kotlin-javaFX \
  --group com.mycompany \
  --config jdk_version=23 \
  --config app_name="My JavaFX App"
```

## Template Structure

Each template follows this structure:

```
template-name/
├── TEMPLATE.md              # Metadata with YAML frontmatter
├── build.gradle.kts         # Jinja2 template
├── settings.gradle.kts      # Jinja2 template
├── gradle.properties        # Jinja2 template or static
├── .gitignore              # Static or template
├── .editorconfig           # Static
├── gradle/
│   └── libs.versions.toml  # Jinja2 template
└── src/
    └── main/
        └── kotlin/
            └── {{ group | package_path }}/
                └── Main.kt  # Jinja2 template
```

## Creating Custom Templates

See [TEMPLATE_GUIDE.md](TEMPLATE_GUIDE.md) for details on creating your own templates.

## License

MIT
