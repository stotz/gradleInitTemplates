package {{ group }}

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Unit tests for HelloController.
 *
 * The controller is plain Kotlin, so it is tested directly without starting a
 * Spring context - fast and deterministic.
 */
class HelloControllerTest {

    @Test
    fun `hello greets by name`() {
        val body = HelloController().hello("Kotlin")
        assertEquals("Hello, Kotlin!", body["message"])
        assertEquals("{{ project_name }}", body["application"])
    }

    @Test
    fun `hello greets the default name`() {
        val body = HelloController().hello("World")
        assertEquals("Hello, World!", body["message"])
    }
}
