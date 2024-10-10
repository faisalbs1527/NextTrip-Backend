package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightBookingRequest
import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.dto.hotel.BookingResponseBody
import com.example.nexttrip.model.entity.flight.BookingSeatsEntity
import com.example.nexttrip.model.entity.flight.FlightEntity
import com.example.nexttrip.model.entity.flight.TravellerInfoEntity
import com.example.nexttrip.model.mapper.*
import com.example.nexttrip.model.tables.flight.BookingSeats
import org.jetbrains.exposed.sql.transactions.transaction

class FlightRepoImpl : FlightRepository {
    override fun addFlight(flightDataReceive: FlightDataReceive) {
        try {
            transaction {
                val flight = flightDataReceive.toFlightEntity()
                flightDataReceive.baggage.toBaggageEntity(flight)
                flightDataReceive.pricing.forEach { it.toPricingEntity(flight) }
                flightDataReceive.seatPlan.forEach { it.toSeatEntity(flight) }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getFlights(): List<FlightDataReceive> {
        return transaction {
            FlightEntity.all().toList().map { it.toFlightDataReceive() }
        }
    }

    override fun requestFlightBooking(flightBookingRequest: FlightBookingRequest): BookingResponseBody {
        return try {
            transaction {
                val bookingRequest = flightBookingRequest.toFlightBookingEntity()
                flightBookingRequest.travellers.forEach {
                    TravellerInfoEntity.new {
                        bookingId = bookingRequest
                        type = it.type
                        typeNo = it.typeNo
                    }
                }
                val noOfPassenger = flightBookingRequest.travellers.size
                for (i in 1..noOfPassenger){
                    BookingSeatsEntity.new {
                        passengerNo = i
                        bookingId = bookingRequest
                    }
                }
                BookingResponseBody(
                    bookingRequest.id.value,
                    bookingRequest.userID,
                    "Successfully booking request placed!"
                )
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}