package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.dto.hotel.PolicyData
import kotlinx.serialization.Serializable

@Serializable
data class AvailableHotelData(
    val hotelId: String,
    val name: String,
    val location: String,
    val city: String,
    val rating: Double,
    val imageUrl: String,
    val startPriceActual: Int,
    val startPriceDiscount: Int
)
