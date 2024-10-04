package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class RoomData(
    val room_id: String,
    val room_type: String,
    val capacity: Int,
    val bed_type: String,
    val number_of_beds: Int,
    val ac_status: String,
    val availability: Boolean,
    val actual_price: Int,
    val discount_price: Int,
    val image_url: String
)
