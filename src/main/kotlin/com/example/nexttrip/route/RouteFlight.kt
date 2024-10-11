package com.example.nexttrip.route

import com.example.nexttrip.model.dto.flight.*
import com.example.nexttrip.repository.FlightRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.sql.SQLIntegrityConstraintViolationException

fun Route.routeFlight(flightRepository: FlightRepository) {
    route("/flights") {
        post {
            try {
                val flightDetails = call.receive<List<FlightDataReceive>>()
                flightDetails.forEach { flightRepository.addFlight(it) }
                call.respond(HttpStatusCode.OK, "SuccessFully flight data inserted.")
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
        get {
            val flights = flightRepository.getFlights()
            call.respond(flights)
        }
        post("/requestbooking") {
            try {
                val requestBooking = call.receive<FlightBookingRequest>()
                val response = flightRepository.requestFlightBooking(requestBooking)
                call.respond(HttpStatusCode.OK, response)
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
        get("/oneway/{bookingId}") {
            val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid booking ID"
            )
            val availableFlights = flightRepository.getAvailableFlightsOneWay(bookingId)
            call.respond(availableFlights)
        }
        get("/roundway/{bookingId}") {
            val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid booking ID"
            )
            val availableFlights = flightRepository.getAvailableFlightsBothWay(bookingId)
            call.respond(availableFlights)
        }
        post("/selectflight") {
            try {
                val requestFlightSelection = call.receive<FlightSelectionRequestBody>()
                val response = flightRepository.selectFlight(requestFlightSelection)
                call.respond(HttpStatusCode.OK, response)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
            }
        }
        post("/{bookingId}/addtravellersinfo") {
            try {
                val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid booking ID"
                )
                val travellersInfo = call.receive<List<TravellerInfoRequest>>()
                val response = flightRepository.addTravellersInfo(bookingId, travellersInfo)
                call.respond(HttpStatusCode.OK, response)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
            }
        }
        get("/{bookingId}/{returnSeats}") {
            try {
                val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid booking ID"
                )
                val returnSeats = call.parameters["returnSeats"]?.toBoolean() ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Invalid request"
                )
                val seatList = flightRepository.getSeatList(bookingId, returnSeats)
                call.respond(seatList)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
            }
        }
        post("/selectseats") {
            try {
                val seatSelectionRequest = call.receive<SelectSeatRequest>()
                val response = flightRepository.selectSeats(seatSelectionRequest)
                call.respond(HttpStatusCode.OK, response)
            } catch (ex: Exception) {
                call.respond(HttpStatusCode.InternalServerError, ex.message ?: "An unexpected error occurred.")
            }
        }
    }
}