package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class SeatPlanData(
    val seatNumber: String,
    val classType: String,
    val status: String
)
