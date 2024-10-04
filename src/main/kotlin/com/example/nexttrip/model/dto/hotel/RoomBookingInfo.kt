package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class RoomBookingInfo(
    val roomNo: Int,
    val adult: Int,
    val child: Int
)
