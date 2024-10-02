package com.example.nexttrip.route

import com.example.nexttrip.model.dto.BookingData
import com.example.nexttrip.model.dto.CarData
import com.example.nexttrip.model.dto.RequestForm
import com.example.nexttrip.model.dto.TestData
import com.example.nexttrip.repository.CarRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.sql.SQLIntegrityConstraintViolationException

fun Application.routeCar(carRepository: CarRepository) {

    routing {
        route("/nexttrip/cars") {
            get {
                val cars = carRepository.getAllCars()
                call.respond(cars)
            }
            post {
                try {
                    val carDetails = call.receive<CarData>()
                    carRepository.addCar(carDetails)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: SQLIntegrityConstraintViolationException) {
                    call.respond(HttpStatusCode.Conflict, ex.message ?: "Unique constraint violation.")
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest, ex.message ?: "Illegal state.")
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest, ex.message ?: "JSON conversion error.")
                } catch (ex: ExposedSQLException) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "Database error.")
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
                }
            }
            get("/{fromId}/{destinationId}") {
                val fromId = call.parameters["fromId"]?.toIntOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid From ID"
                )
                val destinationId = call.parameters["destinationId"]?.toIntOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid Destination ID"
                )
                val availableCars = carRepository.getCarsByRoute(fromId, destinationId)
                call.respond(availableCars)
            }
            get("/locations") {
                val locations = carRepository.getLocations()
                call.respond(locations)
            }
            post("/confirm") {
                try {
                    val bookingDetails = call.receive<BookingData>()
                    val booking = carRepository.confirmRide(bookingDetails)
                    call.respond(HttpStatusCode.OK, booking)
                } catch (ex: NoSuchElementException) {
                    call.respond(HttpStatusCode.NotFound, ex.message ?: "Resource not found.")
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
                }
            }
            post("/cancelRide") {
                try {
                    val request = call.receive<RequestForm>()
                    carRepository.cancelRide(request.bookingId)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
                }
            }
            post("/update/status") {
                try {
                    val request = call.receive<RequestForm>()
                    carRepository.updateBookingStatus(request.bookingId, request.text)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
                }
            }
            post("/update/rating") {
                try {
                    val request = call.receive<RequestForm>()
                    carRepository.addTripRating(request.bookingId, request.rating)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
                }
            }
        }
    }
}