package com.example.nexttrip.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingData(
    val bookingId: Int = 0,
    val carId: String,
    val userId: String,
    val fromId: Int,
    val toId: Int,
    val status: String = "Pending",
    val price: Double,
    val rating: Double = 0.0
)
