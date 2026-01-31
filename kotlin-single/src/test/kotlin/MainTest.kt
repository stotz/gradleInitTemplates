package {{ group }}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
{% if enable_clikt %}
import com.github.ajalt.clikt.testing.test
import com.github.ajalt.clikt.core.subcommands

class MainTest {
    
    private fun createApp() = App().subcommands(InfoCommand(), GreetCommand())
    
    @Test
    fun `app should show help`() {
        val result = createApp().test("--help")
        assertTrue(result.stdout.contains("{{ project_name }}"))
        assertTrue(result.stdout.contains("greet"))
        assertTrue(result.stdout.contains("info"))
    }
    
    @Test
    fun `greet should greet with default name`() {
        val result = createApp().test("greet")
        assertTrue(result.stdout.contains("Hello, World!"))
    }
    
    @Test
    fun `greet should greet with custom name`() {
        val result = createApp().test("greet --name=Kotlin")
        assertTrue(result.stdout.contains("Hello, Kotlin!"))
    }
    
    @Test
    fun `greet should greet multiple times`() {
        val result = createApp().test("greet --count=3")
        val count = result.stdout.split("Hello").size - 1
        assertEquals(3, count)
    }
    
    @Test
    fun `greet should support uppercase`() {
        val result = createApp().test("greet -n Test -u")
        assertTrue(result.stdout.contains("HELLO, TEST!"))
    }
    
    @Test
    fun `info command should work`() {
        val result = createApp().test("info")
        assertTrue(result.stdout.contains("{{ project_name }}"))
        assertTrue(result.stdout.contains("Version"))
    }
    
    @Test
    fun `app without subcommand should show help`() {
        val result = createApp().test("")
        assertTrue(result.stdout.contains("{{ project_name }}"))
        assertTrue(result.stdout.contains("Quick Start"))
    }
}
{% else %}

class MainTest {
    
    @Test
    fun `application should start successfully`() {
        val app = {{ project_name | PascalCase }}()
        assertNotNull(app)
    }
    
    @Test
    fun `run with help should not throw`() {
        val app = {{ project_name | PascalCase }}()
        assertDoesNotThrow {
            app.run(arrayOf("--help"))
        }
    }
    
    @Test
    fun `run with info should not throw`() {
        val app = {{ project_name | PascalCase }}()
        assertDoesNotThrow {
            app.run(arrayOf("--info"))
        }
    }
}
{% endif %}
