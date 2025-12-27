package {{ group }}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Simple test for {{ app_name }}.
 * 
 * Note: For JavaFX UI testing, consider using TestFX:
 * https://github.com/TestFX/TestFX
 */
class AppTest {

    @Test
    fun `test app class exists`() {
        val appClass = App::class.java
        assertNotNull(appClass)
    }

    @Test
    fun `test launcher class exists`() {
        val launcherClass = LauncherApp::class.java
        assertNotNull(launcherClass)
    }

    @Test
    fun `test advanced demo class exists`() {
        val advancedClass = AdvancedDemo::class.java
        assertNotNull(advancedClass)
    }
}
