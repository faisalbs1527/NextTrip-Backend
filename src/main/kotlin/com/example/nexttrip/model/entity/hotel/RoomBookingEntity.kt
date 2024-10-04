package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.RoomBooking
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoomBookingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RoomBookingEntity>(RoomBooking)

    val bookingId by HotelBookingEntity referencedOn RoomBooking.bookingId
    var roomNo by RoomBooking.roomNo
    var adult by RoomBooking.adult
    var child by RoomBooking.child
    var selectedRoom by RoomEntity.optionalReferencedOn(RoomBooking.selectedRoom)
}
