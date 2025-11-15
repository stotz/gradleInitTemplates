package {{ group }}

/**
 * Main entry point for {{ project_name }}
 * 
 * Generated: {{ date }}
 * {% if config('custom.author') %}@author {{ config('custom.author') }}{% endif %}
 */
fun main() {
    val app = {{ project_name | PascalCase }}()
    app.run()
}

class {{ project_name | PascalCase }} {
    fun run() {
        println("Welcome to {{ project_name }}!")
        println("Built with Kotlin {{ kotlin_version }}")
        {% if config('custom.company') %}
        println("© {{ config('custom.company') }}")
        {% endif %}
    }
}
