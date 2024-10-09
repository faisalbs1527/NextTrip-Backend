package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object Seats : IntIdTable("flightseats") {
    val flightId = reference("flight_id", Flights)
    val seatNumber = varchar("seat_number", 5)
    val classType = varchar("class_type", 20) // 'Economy' or 'Business'
    val status = varchar("status", 20)
}