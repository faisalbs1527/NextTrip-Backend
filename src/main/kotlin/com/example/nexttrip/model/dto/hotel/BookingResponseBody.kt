package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class BookingResponseBody(
    val bookingId: Int,
    val userId: String,
    val message: String,
)