package {{ group }}.app

import {{ group }}.lib.Greeter

fun main() {
    val greeter = Greeter()
    println(greeter.greet("{{ project_name }}"))
}
