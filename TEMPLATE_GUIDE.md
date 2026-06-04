# Template Creation Guide

## Template Structure

A template is a directory containing files that will be processed by Jinja2 and copied to the new project.

### Required Files

1. **TEMPLATE.md** - Metadata and documentation
2. **build.gradle.kts** - Build configuration
3. **settings.gradle.kts** - Settings configuration

### TEMPLATE.md Format

```yaml
---
name: Template Name
description: Short description
version: 1.0.0
tags: [kotlin, gradle, tag1, tag2]

requirements:
  gradle: ">=8.0"
  kotlin: ">=2.0"
  jdk: ">=24"

arguments:
  - name: group
    type: string
    help: Maven group ID
    context_key: group
    default: com.example
    required: true
    
  - name: version
    type: string
    help: Project version
    context_key: version
    default: "1.0.0"
    required: false
---

# Template Documentation

Markdown documentation for the template.
```

## Available Variables

### Standard Variables

- `{{ project_name }}` - Project name
- `{{ group }}` - Maven group ID
- `{{ version }}` - Project version
- `{{ kotlin_version }}` - Kotlin version
- `{{ gradle_version }}` - Gradle version
- `{{ jdk_version }}` - JDK version
- `{{ date }}` - Current date
- `{{ author }}` - Author name (from config)

### Custom Variables

Access from config or environment:
```jinja2
{{ config('custom.author', 'Unknown') }}
{{ config('custom.company', 'Company AG') }}
{{ env('GRADLE_INIT_AUTHOR', 'Default Author') }}
```

## Jinja2 Filters

### Case Conversion

```jinja2
{{ name | camelCase }}   # myProject
{{ name | PascalCase }}  # MyProject
{{ name | snake_case }}  # my_project
{{ name | kebab_case }}  # my-project
```

### Package Path

```jinja2
{{ group | package_path }}  # com.example → com/example
```

## Dynamic Directories

Use Jinja2 in directory names:

```
src/main/kotlin/{{ group | package_path }}/
```

## Conditionals

```jinja2
{% if config('spring.enabled', false) %}
implementation("org.springframework.boot:spring-boot-starter-web")
{% endif %}
```

## Loops

```jinja2
{% for module in config('modules', []) %}
include("{{ module }}")
{% endfor %}
```

## Example Template

See the `kotlin-single` template for a complete example.

## Testing Your Template

```bash
./gradleInit.py init test-project --template /path/to/your/template --group com.test
```

## Best Practices

1. **Use version catalogs** for dependency management
2. **Provide sensible defaults** for all variables
3. **Document custom config keys** in TEMPLATE.md
4. **Test with different configurations**
5. **Follow Gradle best practices**
6. **Use EditorConfig** for consistent formatting

## Publishing

To make your template available:

1. Fork this repository
2. Add your template directory
3. Update the main README.md
4. Submit a pull request

## License

MIT
