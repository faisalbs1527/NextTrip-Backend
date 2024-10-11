package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object BookingSeats : IntIdTable("bookingseats") {
    val passengerNo = integer("passenger_no")
    val bookingId = reference("booking_id", FlightBookingInfo)
    val seatId = reference("seat_id", Seats).nullable()
    val status = varchar("status",20)
}