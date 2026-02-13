package {{ group }}

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable

@Serializable
data class Message(val message: String, val application: String)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    
    routing {
        get("/") {
            call.respond(Message(
                message = "Welcome to {{ project_name }}!",
                application = "{{ project_name }}"
            ))
        }
        
        get("/hello") {
            val name = call.request.queryParameters["name"] ?: "World"
            call.respond(Message(
                message = "Hello, $name!",
                application = "{{ project_name }}"
            ))
        }
    }
}
