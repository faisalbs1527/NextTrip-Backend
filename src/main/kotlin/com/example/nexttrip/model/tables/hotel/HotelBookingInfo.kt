package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object HotelBookingInfo : IntIdTable("hotelbooking") {
    val hotel = reference("hotel_id",Hotels).nullable()
    val userID = varchar("user_id", 10)
    val status = varchar("status", 20)
    val location = varchar("location",30)
    val checkInDate = varchar("check_in",30)
    val checkOutDate = varchar("check_out",30)
    val payment = integer("payment").nullable()
}