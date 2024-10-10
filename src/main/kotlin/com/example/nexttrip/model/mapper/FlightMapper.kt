package com.example.nexttrip.model.mapper

import com.example.nexttrip.model.dto.flight.BaggageData
import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.dto.flight.PricingDataReceive
import com.example.nexttrip.model.dto.flight.SeatPlanDataReceive
import com.example.nexttrip.model.entity.flight.BaggageEntity
import com.example.nexttrip.model.entity.flight.FlightEntity
import com.example.nexttrip.model.entity.flight.PricingEntity
import com.example.nexttrip.model.entity.flight.SeatEntity

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