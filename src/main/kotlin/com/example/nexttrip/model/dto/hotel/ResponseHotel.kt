package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class ResponseHotel(
    val hotelId: String,
    val name: String,
    val location: String,
    val city: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val description: String,
    val checkInTime: String,
    val checkOutTime: String,
    val startPriceActual: Int,
    val startPriceDiscount: Int,
    val imageUrl: String,
    val policies: PolicyData,
    val complimentaryServices: List<String>,
)
