package com.example.routes

import com.example.models.Car
import com.example.models.Cars
import com.example.models.Police
import com.example.models.polices
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.policeCars() {
    route("/policeCar") {
        get {
            if (Cars.isNotEmpty()) {
                call.respond(Cars)
            } else {
                call.respondText("No cars found", status = HttpStatusCode.OK)
            }
        }
        get("{carRegistration?}") {
            val carRegistration = call.parameters["carRegistration"] ?: return@get call.respondText(
                "Missing carRegistration",
                status = HttpStatusCode.BadRequest
            )
            val car =
                Cars.find { it.carRegistration == carRegistration } ?: return@get call.respondText(
                    "No car with dni $carRegistration",
                    status = HttpStatusCode.NotFound
                )
            call.respond(car)
        }

        get("/filterAvailable/{available?}"){
            val available = call.parameters["available"] ?: return@get call.respondText(
                "not available",
                status = HttpStatusCode.BadRequest
            )
            val filterCars = Cars.filter {  it.available.toString() == available }
            call.respond(filterCars)
        }

        get("/filterDamaged/{damaged?}"){
            val damaged = call.parameters["damaged"] ?: return@get call.respondText(
                "not available",
                status = HttpStatusCode.BadRequest
            )
            val filterCars = Cars.filter {  it.damaged.toString() == damaged }
            call.respond(filterCars)
        }

        get("/filterCarRegistrationAdvanced/{carRegistration?}"){
            val carRegistration = call.parameters["carRegistration"] ?: return@get call.respondText(
                "not available",
                status = HttpStatusCode.BadRequest
            )
            val filterCars = Cars.filter {  it.carRegistration.contains(carRegistration) }
            call.respond(filterCars)
        }


        post {
            val newCar = call.receive<Car>()
            Cars.add(newCar)
            call.respondText("New car", status = HttpStatusCode.Created)
        }

        put("/damaged/{carRegistration?}") {
            val carRegistration = call.parameters["carRegistration"] ?: return@put call.respondText(
                "Missing carRegistration",
                status = HttpStatusCode.BadRequest
            )

            var carUpdate = Cars.find { it.carRegistration == carRegistration }?: return@put call.respondText(
                "No car with dni $carRegistration",
                status = HttpStatusCode.NotFound
            )
            carUpdate.damaged = true
            carUpdate.available = false

            call.respondText("Update car", status = HttpStatusCode.OK)

        }
    }
}