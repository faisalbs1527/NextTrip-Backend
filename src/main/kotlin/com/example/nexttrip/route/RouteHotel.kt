package com.example.nexttrip.route

import com.example.nexttrip.model.dto.hotel.BookingRequestBody
import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.repository.HotelRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import java.sql.SQLIntegrityConstraintViolationException

fun Route.routeHotel(hotelRepository: HotelRepository) {
    route("/hotels") {
        get {
            val hotels = hotelRepository.getAllHotels()
            call.respond(hotels)
        }
        post {
            try {
                val hotelDetails = call.receive<HotelReceiveData>()
                hotelRepository.addHotel(hotelDetails)
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
        post("/requestbooking") {
            try {
                val bookingInfo = call.receive<BookingRequestBody>()
                hotelRepository.requestBooking(bookingInfo)
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
        get("/{bookingId}") {
            val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid booking ID"
            )
            val availableHotels = hotelRepository.getAvailableHotels(bookingId)
            call.respond(availableHotels)
        }
        get("/{bookingId}/{hotelId}//details") {
            val bookingId = call.parameters["bookingId"]?.toIntOrNull() ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid booking ID"
            )
            val hotelId = call.parameters["hotelId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Invalid From ID"
            )
            val hotelDetails = hotelRepository.getHotelDetails(bookingId,hotelId)
            call.respond(hotelDetails)
        }
    }
}