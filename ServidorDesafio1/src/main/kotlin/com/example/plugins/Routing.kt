package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import rutas.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Servidor funcionando")
        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
        rutasUsuario()
        rutasNave()
        rutasMision()
        rutasVuelos()
        rutasBombardero()
        rutasCombate()
    }
}
