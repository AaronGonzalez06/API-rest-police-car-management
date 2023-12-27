package com.example.routes

import com.example.models.Police
import com.example.models.polices
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.policesOption() {
    route("/police") {
        get {
            if (polices.isNotEmpty()) {
                call.respond(polices)
            } else {
                call.respondText("No polices found", status = HttpStatusCode.OK)
            }
        }
        get("{dni?}") {
            val dni = call.parameters["dni"] ?: return@get call.respondText(
                "Missing DNI",
                status = HttpStatusCode.BadRequest
            )
            val police =
                polices.find { it.dni == dni } ?: return@get call.respondText(
                    "No police with dni $dni",
                    status = HttpStatusCode.NotFound
                )
            call.respond(police)
        }

        get("/filter/{range?}/{available?}"){
            val range = call.parameters["range"] ?: false
            val available = call.parameters["available"] ?: false
            if(range == false && available == false){
                return@get call.respondText(
                    "nothing to filter",
                    status = HttpStatusCode.NotFound
                )
            }

            if( available == false){
                val filterPolices = polices.filter { it.range  == range }
                call.respond(filterPolices)
            }

            if(range == false){
                val filterPolices = polices.filter {it.available.toString() == available }
                call.respond(filterPolices)
            }

            val filterPolices = polices.filter { it.range  == range && it.available.toString() == available }
            call.respond(filterPolices)
        }

        get("/filter/{available?}"){
            val available = call.parameters["available"] ?: return@get call.respondText(
                "not available",
                status = HttpStatusCode.BadRequest
            )
            val filterPolices = polices.filter {  it.available.toString() == available }
            call.respond(filterPolices)
        }

        post {
            val newPolice = call.receive<Police>()
            polices.add(newPolice)
            call.respondText("New Police", status = HttpStatusCode.Created)
        }
        put("/available/{dni?}/{available?}") {
            val dni = call.parameters["dni"] ?: return@put call.respondText(
                "Missing DNI",
                status = HttpStatusCode.BadRequest
            )

            val available = call.parameters["available"] ?: return@put call.respondText(
                "Missing available",
                status = HttpStatusCode.BadRequest
            )

            var policeUpdate = polices.find { it.dni == dni }?: return@put call.respondText(
                "No police with dni $dni",
                status = HttpStatusCode.NotFound
            )
            policeUpdate.available = available.toBoolean()

            call.respondText("Update police", status = HttpStatusCode.OK)

        }

        put("/range/{dni?}/{range?}") {

            val ranges = listOf("Official", "Sergeant", "Nothing")

            val dni = call.parameters["dni"] ?: return@put call.respondText(
                "Missing DNI",
                status = HttpStatusCode.BadRequest
            )

            val range = call.parameters["range"] ?: return@put call.respondText(
                "Missing range",
                status = HttpStatusCode.BadRequest
            )
            val check = range in ranges

            if (!check) {
                return@put call.respondText(
                    "range not allowed",
                    status = HttpStatusCode.NotFound
                )
            }

            var policeUpdate = polices.find { it.dni == dni }?: return@put call.respondText(
                "No police with dni $dni",
                status = HttpStatusCode.NotFound
            )
            policeUpdate.range = range

            call.respondText("Update police", status = HttpStatusCode.OK)

        }
    }
}