package com.example.nexttrip.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class CarData(
    val carId: String,
    val latitude: Double,
    val longitude: Double,
    val rotation: Int,
    val carName: String,
    val model: String,
    val riderName: String,
    val fuelType: String,
    val gearType: String,
    val color: String,
    val image: String,
    val successfulRides: Int,
    val reviews: Int,
    val rating: Double,
    val availableRoutes: List<RouteData>
)
