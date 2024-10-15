package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class SelectSeatRequest(
    val bookingId: Int,
    val returnSeat: Boolean,
    val selectedSeats: List<String>
)
