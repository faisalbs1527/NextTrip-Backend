package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightBookingRequest
import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.dto.flight.FlightDetailsResponse
import com.example.nexttrip.model.dto.hotel.BookingResponseBody
import com.example.nexttrip.model.entity.flight.*
import com.example.nexttrip.model.mapper.*
import com.example.nexttrip.model.tables.flight.BookingSeats
import com.example.nexttrip.model.tables.flight.Flights
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
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
                for (i in 1..noOfPassenger) {
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

    override fun getAvailableFlightsOneWay(bookingId: Int): List<FlightDetailsResponse> {
        return transaction {
            val bookingDetails = FlightBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            getAvailableFlights(bookingDetails, bookingDetails.departureAirport, bookingDetails.arrivalAirport)
        }
    }

    override fun getAvailableFlightsBothWay(bookingId: Int): List<Pair<FlightDetailsResponse, FlightDetailsResponse>> {
        return transaction {
            val bookingDetails = FlightBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            val departureFlight =
                getAvailableFlights(bookingDetails, bookingDetails.departureAirport, bookingDetails.arrivalAirport)
            val returnFlight =
                getAvailableFlights(bookingDetails, bookingDetails.arrivalAirport, bookingDetails.departureAirport)
            val bothWayFlights = generateRoundWayFlights(departureFlight, returnFlight)
            bothWayFlights
        }
    }

    override fun addTravellersInfo(): BookingResponseBody {
        TODO("Not yet implemented")
    }

    override fun getAvailableSeats() {
        TODO("Not yet implemented")
    }

    override fun selectSeats(): BookingResponseBody {
        TODO("Not yet implemented")
    }

    override fun getBookingDetails() {
        TODO("Not yet implemented")
    }

    override fun confirmBooking(): BookingResponseBody {
        TODO("Not yet implemented")
    }
}

private fun getTheNumberOfAvailableSeats(seatList: List<SeatEntity>, classType: String): Int {
    var seatCount = 0
    seatList.forEach {
        if (it.classType == classType && it.status == "Available") {
            seatCount++
        }
    }
    return seatCount
}

private fun getAvailableFlights(
    bookingDetails: FlightBookingEntity,
    departureAirport: String,
    arrivalAirport: String
): List<FlightDetailsResponse> {
    val flights = FlightEntity.find {
        (Flights.departureAirport eq departureAirport) and
                (Flights.arrivalAirport eq arrivalAirport)
    }
    val availableFlights = flights.filter {
        (getTheNumberOfAvailableSeats(
            it.seats.toList(),
            bookingDetails.classType
        ) >= bookingDetails.travellers.toList().size)
    }
    return availableFlights.map { flight ->
        val price = flight.pricing.find { it.classType == bookingDetails.classType }?.price
        flight.toFlightDataResponse(price!!, bookingDetails.classType)
    }
}

private fun generateRoundWayFlights(
    outgoingFlights: List<FlightDetailsResponse>,
    incomingFlights: List<FlightDetailsResponse>
): List<Pair<FlightDetailsResponse, FlightDetailsResponse>> {
    val combinations = mutableListOf<Pair<FlightDetailsResponse, FlightDetailsResponse>>()
    for (outgoing in outgoingFlights) {
        for (incoming in incomingFlights) {
            combinations.add(Pair(outgoing, incoming))
        }
    }
    return combinations
}