package com.example.nexttrip.model.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class LocationData(
    val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
