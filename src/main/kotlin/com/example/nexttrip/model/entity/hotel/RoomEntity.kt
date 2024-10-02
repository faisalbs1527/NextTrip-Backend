package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.Rooms
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoomEntity (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomEntity>(Rooms)

    var roomNo by Rooms.room_no
    var hotel by HotelEntity referencedOn Rooms.hotel_id
    var roomType by Rooms.room_type
    var capacity by Rooms.capacity
    var bedType by Rooms.bed_type
    var numberOfBeds by Rooms.number_of_beds
    var acStatus by Rooms.ac_status
    var availability by Rooms.availability
    var actualPrice by Rooms.actual_price
    var discountPrice by Rooms.discount_price
    var imageUrl by Rooms.image_url
}