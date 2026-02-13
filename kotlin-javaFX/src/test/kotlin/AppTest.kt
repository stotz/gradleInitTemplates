package {{ group }}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Simple test for {{ app_name | default(project_name) }}.
 * 
 * Note: For JavaFX UI testing, consider using TestFX:
 * https://github.com/TestFX/TestFX
 */
class AppTest {

    @Test
    fun `test main class exists`() {
        val mainClass = Main::class.java
        assertNotNull(mainClass)
    }

    @Test
    fun `test app class exists`() {
        val appClass = App::class.java
        assertNotNull(appClass)
    }

    @Test
    fun `test main method exists`() {
        val mainMethod = Main::class.java.methods.find { it.name == "run" }
        assertNotNull(mainMethod)
    }
}
