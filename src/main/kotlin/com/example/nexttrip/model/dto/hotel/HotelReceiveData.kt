package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class HotelReceiveData(
    var hotel_id: String,
    var name: String,
    var location: String,
    var city: String,
    var latitude: Double,
    var longitude: Double,
    var rating: Double,
    var description: String,
    var check_in_time: String,
    var check_out_time: String,
    var image_url: String,
    var policies: PolicyData,
    var complimentary_services: List<String>,
    var rooms: List<RoomData>
)
