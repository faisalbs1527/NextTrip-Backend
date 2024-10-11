package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class FlightSelectionRequestBody(
    val bookingId: Int,
    val departureFlightNumber: String,
    val returnFlightNumber: String? = null
)
