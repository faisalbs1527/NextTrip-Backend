package com.example.nexttrip.model.mapper

import com.example.nexttrip.model.dto.flight.*
import com.example.nexttrip.model.entity.flight.*

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

fun SeatPlanDataReceive.toSeatEntity(flightEntity: FlightEntity) = SeatEntity.new {
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
    seatPlan = seats.toList().map { SeatPlanDataReceive(it.seatNumber, it.classType, it.status) },
    baggage = baggage.toList().map { BaggageData(it.checkedAllowance, it.carryOnAllowance) }.first()
)

fun FlightBookingRequest.toFlightBookingEntity() = FlightBookingEntity.new {
    userID = this@toFlightBookingEntity.userId
    status = this@toFlightBookingEntity.status
    departureAirport = this@toFlightBookingEntity.departureAirport
    arrivalAirport = this@toFlightBookingEntity.arrivalAirport
    travelDate = this@toFlightBookingEntity.travelDate
    classType = this@toFlightBookingEntity.classType
}