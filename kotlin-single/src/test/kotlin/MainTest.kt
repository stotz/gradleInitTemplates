package {{ group }}

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class MainTest {
    
    @Test
    fun `application should start successfully`() {
        val app = {{ project_name | PascalCase }}()
        assertNotNull(app)
    }
    
    @Test
    fun `run should execute without exceptions`() {
        val app = {{ project_name | PascalCase }}()
        assertDoesNotThrow {
            app.run()
        }
    }
}
