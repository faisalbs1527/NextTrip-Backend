package com.example.nexttrip.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class RouteData(
    val fromLocation: LocationData,
    val toLocation: LocationData
)
