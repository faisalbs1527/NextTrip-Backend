package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object HotelService : IntIdTable("hotelservice") {
    val hotel = reference("hotel_id", Hotels)
    val service = reference("service_id", Services)

    init {
        uniqueIndex(hotel, service)
    }
}