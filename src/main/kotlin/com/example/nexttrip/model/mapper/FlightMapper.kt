package com.example.nexttrip.model.mapper

import com.example.nexttrip.model.dto.flight.*
import com.example.nexttrip.model.entity.flight.*
import org.jetbrains.exposed.dao.id.EntityID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

fun FlightDataReceive.toFlightEntity() = FlightEntity.new {
    flightNumber = this@toFlightEntity.flightNumber
    departureAirport = this@toFlightEntity.departureAirport
    arrivalAirport = this@toFlightEntity.arrivalAirport
    departureTime = this@toFlightEntity.departureTime
    arrivalTime = this@toFlightEntity.arrivalTime
    airline = this@toFlightEntity.airline
    departureGate = this@toFlightEntity.departureGate
    arrivalGate = this@toFlightEntity.arrivalGate
}

fun PricingDataReceive.toPricingEntity(flightEntity: FlightEntity) = PricingEntity.new {
    flightId = flightEntity.id
    classType = this@toPricingEntity.classType
    price = this@toPricingEntity.price
    currency = this@toPricingEntity.currency
}

fun SeatPlanData.toSeatEntity(flightEntity: FlightEntity) = SeatEntity.new {
    flightId = flightEntity.id
    seatNumber = this@toSeatEntity.seatNumber
    classType = this@toSeatEntity.classType
    status = this@toSeatEntity.status
}

fun BaggageData.toBaggageEntity(flightEntity: FlightEntity) = BaggageEntity.new {
    flightId = flightEntity.id
    checkedAllowance = this@toBaggageEntity.checkedAllowance
    carryOnAllowance = this@toBaggageEntity.carryOnAllowance
}

fun FlightEntity.toFlightDataReceive() = FlightDataReceive(
    flightNumber = flightNumber,
    departureAirport = departureAirport,
    arrivalAirport = arrivalAirport,
    departureTime = departureTime,
    arrivalTime = arrivalTime,
    airline = airline,
    departureGate = departureGate,
    arrivalGate = arrivalGate,
    pricing = pricing.toList().map { PricingDataReceive(it.classType, it.price, it.currency) },
    seatPlan = seats.toList().map { SeatPlanData(it.seatNumber, it.classType, it.status) },
    baggage = baggage.toList().map { BaggageData(it.checkedAllowance, it.carryOnAllowance) }.first()
)

fun FlightBookingRequest.toFlightBookingEntity() = FlightBookingEntity.new {
    userID = this@toFlightBookingEntity.userId
    status = this@toFlightBookingEntity.status
    departureAirport = this@toFlightBookingEntity.departureAirport
    arrivalAirport = this@toFlightBookingEntity.arrivalAirport
    departureDate = this@toFlightBookingEntity.departureDate
    returnDate = this@toFlightBookingEntity.returnDate
    classType = this@toFlightBookingEntity.classType
    flightType = this@toFlightBookingEntity.flightType
}

fun FlightEntity.toFlightDataResponse(price: Double, classType: String) = FlightDetailsResponse(
    flightNumber = flightNumber,
    departureAirport = departureAirport,
    arrivalAirport = arrivalAirport,
    departureTime = departureTime,
    arrivalTime = arrivalTime,
    airline = airline,
    departureGate = departureGate,
    arrivalGate = arrivalGate,
    price = price,
    currency = pricing.first().currency,
    classType = classType,
    duration = getDuration(departureTime, arrivalTime),
    baggage = baggage.toList().map { BaggageData(it.checkedAllowance, it.carryOnAllowance) }.first()
)

fun FlightBookingEntity.toBookingResponse() =
    FlightBookingResponse(
        departureFlight = getFlight(departureFlight, classType)!!,
        returnFlight = getFlight(returnFlight, classType),
        userId = userID,
        departureAirport = departureAirport,
        arrivalAirport = arrivalAirport,
        departureDate = departureDate,
        returnDate = returnDate,
        classType = classType,
        flightType = flightType,
        payment = payment ?: 0,
        travellers = travellers.toList().map { it.toTravellerData() },
        selectedSeatsDeparture = getSeats(selectedSeats.toList(), false)!!,
        selectedSeatsReturn = getSeats(selectedSeats.toList(), true)
    )

fun TravellerInfoEntity.toTravellerData() = TravellerInfoData(
    type, typeNo, title, firstName, lastName, dateOfBirth
)

fun getSeats(selectedSeats: List<BookingSeatsEntity>, returnSeat: Boolean): String? {
    var seats = ""
    selectedSeats.map {
        if (returnSeat) {
            if (it.status == "Return") {
                seats += "-${it.seatId?.seatNumber}"
            }
        } else {
            if (it.status == "Departure") {
                seats += "-${it.seatId?.seatNumber}"
            }
        }
    }
    if (seats.isEmpty()) return null
    return seats.substring(1)
}

fun getFlight(flightId: EntityID<Int>?, classType: String): FlightDetailsResponse? {
    val flight = flightId?.let { FlightEntity.findById(it) }
    if (flight != null) {
        val price = flight.pricing.find { it.classType == classType }?.price
        return flight.toFlightDataResponse(price!!, classType)
    } else {
        return null
    }
}

fun SeatEntity.toSeatPlanData() = SeatPlanData(
    seatNumber, classType, status
)

fun getDuration(startDateTime: String, endDateTime: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME

    val departureTime = LocalDateTime.parse(startDateTime, formatter)
    val arrivalTime = LocalDateTime.parse(endDateTime, formatter)
    val duration = Duration.between(departureTime, arrivalTime)
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60

    return buildString {
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m")
    }.trim()
}