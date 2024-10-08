package com.example.nexttrip.model.dto.hotel

data class BookingResponseBody(
    val bookingId: Int,
    val userId: String,
    val message: String,
)