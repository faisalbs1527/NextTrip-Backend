package com.example.nexttrip.model.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class RouteData(
    val fromLocation: LocationData,
    val toLocation: LocationData
)
