package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object Flights: IntIdTable("flights") {
    val flightNumber = varchar("flight_number", 20).uniqueIndex()
    val departureAirport = varchar("departure_airport", 5)
    val arrivalAirport = varchar("arrival_airport", 5)
    val departureTime = varchar("departure_time",50)
    val arrivalTime = varchar("arrival_time",50)
    val airline = varchar("airline", 100)
    val departureGate = varchar("departure_gate",10)
    val arrivalGate = varchar("arrival_gate",10)
}