package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object RoomBooking : IntIdTable("roombooking") {
    val bookingId = reference("booking_id", HotelBookingInfo)
    val roomNo = integer("room_no")
    val adult = integer("adult")
    val child = integer("child")
    val selectedRoom = reference("selected_room", Rooms).nullable()
}