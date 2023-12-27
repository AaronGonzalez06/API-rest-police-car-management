package com.example.routes

import com.example.models.Cars
import com.example.models.polices
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.policeCarsAdd() {
    route("/addPoliceCar") {
        put("/{carRegistration}/{dniPolice}") {
            val carRegistration = call.parameters["carRegistration"] ?: return@put call.respondText(
                "Missing carRegistration",
                status = HttpStatusCode.BadRequest
            )

            val dniPolice = call.parameters["dniPolice"] ?: return@put call.respondText(
                "Missing dniPolice",
                status = HttpStatusCode.BadRequest
            )

            var policeUpdate = polices.find { it.dni == dniPolice }?: return@put call.respondText(
                "No police with dni $dniPolice",
                status = HttpStatusCode.NotFound
            )
            policeUpdate.available = false


            var carUpdate = Cars.find { it.carRegistration == carRegistration }?: return@put call.respondText(
                "No car with dni $carRegistration",
                status = HttpStatusCode.NotFound
            )

            carUpdate.available = false
            carUpdate.polices.add(policeUpdate)


            call.respondText("Update car", status = HttpStatusCode.OK)

        }

        put("/delete/{carRegistration}/{dniPolice}") {
            val carRegistration = call.parameters["carRegistration"] ?: return@put call.respondText(
                "Missing carRegistration",
                status = HttpStatusCode.BadRequest
            )

            val dniPolice = call.parameters["dniPolice"] ?: return@put call.respondText(
                "Missing dniPolice",
                status = HttpStatusCode.BadRequest
            )

            var policeUpdate = polices.find { it.dni == dniPolice }?: return@put call.respondText(
                "No police with dni $dniPolice",
                status = HttpStatusCode.NotFound
            )
            policeUpdate.available = true


            var carUpdate = Cars.find { it.carRegistration == carRegistration }?: return@put call.respondText(
                "No car with dni $carRegistration",
                status = HttpStatusCode.NotFound
            )

            carUpdate.available = false
            carUpdate.polices.remove(policeUpdate)

            if( carUpdate.polices.size == 0) carUpdate.available = true


            call.respondText("Update car", status = HttpStatusCode.OK)

        }
    }
}