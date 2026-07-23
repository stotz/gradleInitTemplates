package {{ group }}

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * HTTP tests for {{ project_name }}.
 *
 * testApplication loads the module configured in application.yaml, so these
 * tests exercise the real routing and content negotiation setup.
 */
class ApplicationTest {

    @Test
    fun `root endpoint responds with welcome message`() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("{{ project_name }}"))
    }

    @Test
    fun `hello endpoint greets by name`() = testApplication {
        val response = client.get("/hello?name=Kotlin")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Hello, Kotlin!"))
    }

    @Test
    fun `hello endpoint uses default name`() = testApplication {
        val response = client.get("/hello")
        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Hello, World!"))
    }
}
