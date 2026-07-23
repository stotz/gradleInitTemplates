package {{ group }}.lib

import kotlin.test.Test
import kotlin.test.assertEquals

class GreeterTest {

    @Test
    fun `greet formats the name`() {
        assertEquals("Hello from Kotlin!", Greeter().greet("Kotlin"))
    }
}
