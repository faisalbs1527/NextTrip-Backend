package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class BookingRequestBody(
    var hotelId: String = "",
    val userId: String,
    val checkIn: String,
    val checkOut: String,
    val location: String,
    var payment: Int = 0,
    var status: String = "Pending",
    var rooms: List<RoomBookingInfo>
)
