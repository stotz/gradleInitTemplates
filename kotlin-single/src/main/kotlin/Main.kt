package {{ group }}
{% if enable_clikt %}

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

/**
 * Main entry point for {{ project_name }}
 *
 * A CLI application built with Clikt.
 */
fun main(args: Array<String>) = App()
    .subcommands(InfoCommand())
    .main(args)

/**
 * Main application command.
 */
class App : CliktCommand(name = "{{ project_name }}") {

    override fun help(context: Context): String = """
        {{ project_name }} - A Kotlin CLI application.
        
        Use --help on any command for more information.
    """.trimIndent()

    private val name by option("-n", "--name", help = "Name to greet").default("World")
    private val count by option("-c", "--count", help = "Number of greetings").int().default(1)

    override fun run() {
        repeat(count) {
            echo("Hello, $name!")
        }
    }
}

/**
 * Info subcommand - displays build and version information.
 */
class InfoCommand : CliktCommand(name = "info") {

    override fun help(context: Context): String = "Display build and version information"

    private val verbose by option("-v", "--verbose", help = "Show all manifest attributes").flag()

    override fun run() {
        val manifest = loadManifest()

        echo("{{ project_name }}")
        echo("=".repeat(40))

        // Always show basic info
        echo("Version:     ${manifest["Implementation-Version"] ?: "unknown"}")
        echo("Vendor:      ${manifest["Implementation-Vendor"] ?: "unknown"}")

        // Git info (if available)
        manifest["Git-Commit"]?.let { commit ->
            echo("")
            echo("Git Information:")
            echo("  Commit:    $commit")
            manifest["Git-Branch"]?.let { echo("  Branch:    $it") }
            manifest["Git-Tag"]?.let { if (it != "none") echo("  Tag:       $it") }
            manifest["Git-Dirty"]?.let { echo("  Dirty:     $it") }
        }

        // Build info (if available)
        manifest["Build-Time"]?.let { buildTime ->
            echo("")
            echo("Build Information:")
            echo("  Time:      $buildTime")
            manifest["Build-OS"]?.let { echo("  OS:        $it") }
            manifest["Build-Host"]?.let { echo("  Host:      $it") }
            manifest["Build-Jdk"]?.let { echo("  JDK:       $it") }
            manifest["Built-By"]?.let { echo("  Built by:  $it") }
        }

        // Verbose: show all attributes
        if (verbose) {
            echo("")
            echo("All Manifest Attributes:")
            manifest.toSortedMap().forEach { (key, value) ->
                echo("  $key: $value")
            }
        }
    }

    private fun loadManifest(): Map<String, String> {
        return try {
            val resources = this::class.java.classLoader.getResources("META-INF/MANIFEST.MF")
            for (url in resources.asSequence()) {
                val manifest = java.util.jar.Manifest(url.openStream())
                val attrs = manifest.mainAttributes
                val title = attrs.getValue("Implementation-Title")
                if (title == "{{ project_name }}") {
                    return attrs.entries.associate { 
                        it.key.toString() to it.value.toString() 
                    }
                }
            }
            emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
{% else %}

/**
 * Main entry point for {{ project_name }}
 *
 * A simple CLI application with --help and --info support.
 */
fun main(args: Array<String>) {
    val app = {{ project_name | PascalCase }}()
    app.run(args)
}

/**
 * Main application class.
 */
class {{ project_name | PascalCase }} {

    fun run(args: Array<String>) {
        when {
            args.isEmpty() -> runDefault()
            args.contains("--help") || args.contains("-h") -> showHelp()
            args.contains("--info") || args.contains("info") -> showInfo(args.contains("--verbose") || args.contains("-v"))
            args.contains("--version") || args.contains("-V") -> showVersion()
            else -> {
                println("Unknown option: ${args.first()}")
                println("Use --help for usage information.")
            }
        }
    }

    private fun runDefault() {
        println("Welcome to {{ project_name }}!")
        println("Use --help for usage information.")
    }

    private fun showHelp() {
        println("""
            |{{ project_name }} - A Kotlin CLI application
            |
            |Usage: {{ project_name }} [OPTIONS] [COMMAND]
            |
            |Commands:
            |  info              Display build and version information
            |
            |Options:
            |  -h, --help        Show this help message
            |  -V, --version     Show version
            |  --info            Same as 'info' command
            |
            |Examples:
            |  {{ project_name }}
            |  {{ project_name }} --help
            |  {{ project_name }} info
            |  {{ project_name }} info --verbose
        """.trimMargin())
    }

    private fun showVersion() {
        val manifest = loadManifest()
        val version = manifest["Implementation-Version"] ?: "unknown"
        println("{{ project_name }} $version")
    }

    private fun showInfo(verbose: Boolean) {
        val manifest = loadManifest()

        println("{{ project_name }}")
        println("=".repeat(40))

        // Always show basic info
        println("Version:     ${manifest["Implementation-Version"] ?: "unknown"}")
        println("Vendor:      ${manifest["Implementation-Vendor"] ?: "unknown"}")

        // Git info (if available)
        manifest["Git-Commit"]?.let { commit ->
            println()
            println("Git Information:")
            println("  Commit:    $commit")
            manifest["Git-Branch"]?.let { println("  Branch:    $it") }
            manifest["Git-Tag"]?.let { if (it != "none") println("  Tag:       $it") }
            manifest["Git-Dirty"]?.let { println("  Dirty:     $it") }
        }

        // Build info (if available)
        manifest["Build-Time"]?.let { buildTime ->
            println()
            println("Build Information:")
            println("  Time:      $buildTime")
            manifest["Build-OS"]?.let { println("  OS:        $it") }
            manifest["Build-Host"]?.let { println("  Host:      $it") }
            manifest["Build-Jdk"]?.let { println("  JDK:       $it") }
            manifest["Built-By"]?.let { println("  Built by:  $it") }
        }

        // Verbose: show all attributes
        if (verbose) {
            println()
            println("All Manifest Attributes:")
            manifest.toSortedMap().forEach { (key, value) ->
                println("  $key: $value")
            }
        }
    }

    private fun loadManifest(): Map<String, String> {
        return try {
            val resources = this::class.java.classLoader.getResources("META-INF/MANIFEST.MF")
            for (url in resources.asSequence()) {
                val manifest = java.util.jar.Manifest(url.openStream())
                val attrs = manifest.mainAttributes
                val title = attrs.getValue("Implementation-Title")
                if (title == "{{ project_name }}") {
                    return attrs.entries.associate { 
                        it.key.toString() to it.value.toString() 
                    }
                }
            }
            emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }
}
{% endif %}
