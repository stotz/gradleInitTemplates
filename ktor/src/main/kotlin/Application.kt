package {{ group }}

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

@Serializable
data class Message(val message: String, val application: String)

fun Application.configureRouting() {
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
