plugins {
    id("kotlin-application-conventions")
}

dependencies {
    implementation(project(":lib"))
}

application {
    mainClass.set("{{ group }}.app.MainKt")
}
