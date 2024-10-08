package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class RequestFormHotel(
    val bookingId: Int,
    val status: String
)
