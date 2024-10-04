package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.HotelService
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class HotelServiceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HotelServiceEntity>(HotelService)

    var hotel by HotelEntity referencedOn HotelService.hotel
    var service by ServiceEntity referencedOn HotelService.service
}