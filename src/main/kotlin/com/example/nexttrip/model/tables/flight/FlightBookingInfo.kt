package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object FlightBookingInfo : IntIdTable("flightbookingInfo") {
    val departureFlight = reference("departure_flight_id", Flights).nullable()
    val returnFlight = reference("return_flight_id", Flights).nullable()
    val userID = varchar("user_id", 10)
    val status = varchar("status", 20)
    val departureAirport = varchar("departure_airport", 5)
    val arrivalAirport = varchar("arrival_airport", 5)
    val departureDate = varchar("departure_date", 30)
    val returnDate = varchar("return_Date", 30).nullable()
    val classType = varchar("class_type", 10)
    val flightType = varchar("flight_type", 10)
    val payment = integer("payment").nullable()
}