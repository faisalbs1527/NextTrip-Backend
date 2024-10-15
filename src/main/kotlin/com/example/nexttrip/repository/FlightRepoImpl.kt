package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.*
import com.example.nexttrip.model.dto.hotel.BookingResponseBody
import com.example.nexttrip.model.entity.flight.*
import com.example.nexttrip.model.mapper.*
import com.example.nexttrip.model.tables.flight.Flights
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
                        status = "Departure"
                    }
                    if (bookingRequest.flightType == "Both Way") {
                        BookingSeatsEntity.new {
                            passengerNo = i
                            bookingId = bookingRequest
                            status = "Return"
                        }
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

    override fun selectFlight(flightSelectionRequestBody: FlightSelectionRequestBody): BookingResponseBody {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(flightSelectionRequestBody.bookingId)
                    ?: throw NoSuchElementException("Booking with ID ${flightSelectionRequestBody.bookingId} does not exist.")
                val departureFlight = FlightEntity.find {
                    Flights.flightNumber eq flightSelectionRequestBody.departureFlightNumber
                }
                bookingDetails.departureFlight = departureFlight.first().id
                if (flightSelectionRequestBody.returnFlightNumber != null) {
                    val returnFlight = FlightEntity.find {
                        Flights.flightNumber eq flightSelectionRequestBody.returnFlightNumber
                    }
                    bookingDetails.returnFlight = returnFlight.first().id
                }
                BookingResponseBody(
                    bookingId = flightSelectionRequestBody.bookingId,
                    userId = bookingDetails.userID,
                    message = "Flight Selection Successfully Done!"
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun addTravellersInfo(bookingId: Int, travellers: List<TravellerInfoData>): BookingResponseBody {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(bookingId)
                    ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
                val travellersInfo = bookingDetails.travellers
                travellers.forEach { traveller ->
                    val travellerDataInDatabase = travellersInfo.find {
                        (it.type == traveller.type) && (it.typeNo == traveller.typeNo)
                    }
                    travellerDataInDatabase?.title = traveller.title
                    travellerDataInDatabase?.firstName = traveller.firstName
                    travellerDataInDatabase?.lastName = traveller.lastName
                    travellerDataInDatabase?.dateOfBirth = traveller.dateOfBirth
                }
                BookingResponseBody(
                    bookingId = bookingId,
                    userId = bookingDetails.userID,
                    message = "Travellers Info Successfully Added!"
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getSeatList(bookingId: Int, returnSeats: Boolean): List<SeatPlanData> {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(bookingId)
                    ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
                val seatList: List<SeatPlanData>
                if (returnSeats) {
                    val selectedFlight = bookingDetails.returnFlight?.let { FlightEntity.findById(it) }
                    seatList = selectedFlight?.seats?.toList()?.map {
                        it.toSeatPlanData()
                    }!!
                } else {
                    val selectedFlight = bookingDetails.departureFlight?.let { FlightEntity.findById(it) }
                    seatList = selectedFlight?.seats?.toList()?.map {
                        it.toSeatPlanData()
                    }!!
                }
                seatList
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }


    override fun selectSeats(selectSeatRequest: SelectSeatRequest): BookingResponseBody {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(selectSeatRequest.bookingId)
                    ?: throw NoSuchElementException("Booking with ID ${selectSeatRequest.bookingId} does not exist.")

                if (selectSeatRequest.returnSeat) {
                    val selectedFlight = FlightEntity.findById(bookingDetails.returnFlight!!)
                    var seatNo = 1
                    selectSeatRequest.selectedSeats.forEach { seatX ->
                        val seatY = selectedFlight?.seats?.find { it.seatNumber == seatX }
                        seatY?.status = "Booked"
                        bookingDetails.selectedSeats.find {
                            (it.passengerNo == seatNo) && (it.status == "Return")
                        }?.seatId = seatY
                        seatNo++
                    }
                    val price = selectedFlight?.pricing?.find { it.classType == bookingDetails.classType }?.price
                    if (price != null) {
                        bookingDetails.payment = bookingDetails.payment!! + price.toInt().times((seatNo - 1))
                    }
                } else {
                    val selectedFlight = FlightEntity.findById(bookingDetails.departureFlight!!)
                    var seatNo = 1
                    bookingDetails.payment = 0
                    selectSeatRequest.selectedSeats.forEach { seatX ->
                        val seatY = selectedFlight?.seats?.find { it.seatNumber == seatX }
                        seatY?.status = "Booked"
                        bookingDetails.selectedSeats.find {
                            (it.passengerNo == seatNo) && (it.status == "Departure")
                        }?.seatId = seatY
                        seatNo++
                    }
                    val price = selectedFlight?.pricing?.find { it.classType == bookingDetails.classType }?.price
                    if (price != null) {
                        bookingDetails.payment = bookingDetails.payment!! + price.toInt().times((seatNo - 1))
                    }
                }
                BookingResponseBody(
                    bookingId = bookingDetails.id.value,
                    userId = bookingDetails.userID,
                    message = "Success!!"
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getBookingDetails(bookingId: Int): FlightBookingResponse {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(bookingId)
                    ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
                bookingDetails.toBookingResponse()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun confirmBooking(bookingId: Int): BookingResponseBody {
        return try {
            transaction {
                val bookingDetails = FlightBookingEntity.findById(bookingId)
                    ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")

                bookingDetails.status = "Confirm"
                BookingResponseBody(
                    bookingId = bookingDetails.id.value,
                    userId = bookingDetails.userID,
                    message = "Flight booking confirmed!!"
                )
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
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