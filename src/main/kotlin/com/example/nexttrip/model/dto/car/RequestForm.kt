package com.example.nexttrip.model.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class RequestForm(
    val bookingId: Int,
    var text: String = "",
    var rating: Double = 0.0
)
