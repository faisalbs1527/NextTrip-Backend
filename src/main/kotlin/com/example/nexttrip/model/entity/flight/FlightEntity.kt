package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.Baggage
import com.example.nexttrip.model.tables.flight.Flights
import com.example.nexttrip.model.tables.flight.Pricing
import com.example.nexttrip.model.tables.flight.Seats
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FlightEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FlightEntity>(Flights)

    var flightNumber by Flights.flightNumber
    var departureAirport by Flights.departureAirport
    var arrivalAirport by Flights.arrivalAirport
    var departureTime by Flights.departureTime
    var arrivalTime by Flights.arrivalTime
    var airline by Flights.airline
    var departureGate by Flights.departureGate
    var arrivalGate by Flights.arrivalGate

    val pricing by PricingEntity referrersOn Pricing.flightId
    val seats by SeatEntity referrersOn Seats.flightId
    val baggage by BaggageEntity referrersOn Baggage.flightId
}