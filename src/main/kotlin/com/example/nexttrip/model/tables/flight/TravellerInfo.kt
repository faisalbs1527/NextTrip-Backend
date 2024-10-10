package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object TravellerInfo : IntIdTable("travellerinfo") {
    val bookingId = reference("booking_id", FlightBookingInfo)
    val type = varchar("type", 20)
    val typeNo = integer("type_no")
    val title = varchar("title", 10).nullable()
    val firstName = varchar("first_name", 30).nullable()
    val lastName = varchar("last_name", 30).nullable()
    val dateOfBirth = varchar("date_of_birth", 30).nullable()
}