package {{ group }}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
{% if enable_clikt %}
import com.github.ajalt.clikt.testing.test
import com.github.ajalt.clikt.core.subcommands

class MainTest {
    
    @Test
    fun `app should show help`() {
        val result = App().test("--help")
        assertTrue(result.stdout.contains("{{ project_name }}"))
    }
    
    @Test
    fun `app should greet with default name`() {
        val result = App().test("")
        assertTrue(result.stdout.contains("Hello, World!"))
    }
    
    @Test
    fun `app should greet with custom name`() {
        val result = App().test("--name=Kotlin")
        assertTrue(result.stdout.contains("Hello, Kotlin!"))
    }
    
    @Test
    fun `app should greet multiple times`() {
        val result = App().test("--count=3")
        val count = result.stdout.split("Hello").size - 1
        assertEquals(3, count)
    }
    
    @Test
    fun `info command should work`() {
        val result = App().subcommands(InfoCommand()).test("info")
        assertTrue(result.stdout.contains("{{ project_name }}"))
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
