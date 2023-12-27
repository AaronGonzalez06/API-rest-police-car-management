package com.example.plugins

import com.example.routes.policeCars
import com.example.routes.policeCarsAdd
import com.example.routes.policesOption
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        policesOption()
        policeCars()
        policeCarsAdd()
    }
}
