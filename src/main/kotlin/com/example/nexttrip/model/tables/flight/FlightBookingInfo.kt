package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object FlightBookingInfo : IntIdTable("flightbookingInfo") {
    val flight = reference("flight_id", Flights).nullable()
    val userID = varchar("user_id", 10)
    val status = varchar("status", 20)
    val departureAirport = varchar("departure_airport", 5)
    val arrivalAirport = varchar("arrival_airport", 5)
    val travelDate = varchar("travel_date",30)
    val classType = varchar("class_type",10)
    val payment = integer("payment").nullable()
}