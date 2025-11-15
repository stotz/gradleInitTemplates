package {{ group }}

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class {{ project_name | PascalCase }}Application

fun main(args: Array<String>) {
    runApplication<{{ project_name | PascalCase }}Application>(*args)
}
