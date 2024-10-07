package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBookingInfo(
    val hotelName: String,
    val location: String,
    val city: String,
    val rating: Double,
    val imageUrl: String,
    val bookingDate: String,
    val checkIn: String,
    val checkOut: String,
    val guests: Int,
    val noOfRooms: Int,
    val paymentActual: String,
    val paymentDiscount:String
)