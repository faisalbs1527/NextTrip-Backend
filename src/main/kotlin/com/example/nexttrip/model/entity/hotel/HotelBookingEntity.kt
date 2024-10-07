package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.HotelBookingInfo
import com.example.nexttrip.model.tables.hotel.Hotels
import com.example.nexttrip.model.tables.hotel.RoomBooking
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class HotelBookingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HotelBookingEntity>(HotelBookingInfo)

    var hotel by HotelBookingInfo.hotel
    var userID by HotelBookingInfo.userID
    var status by HotelBookingInfo.status
    var location by HotelBookingInfo.location
    var checkInDate by HotelBookingInfo.checkInDate
    var checkOutDate by HotelBookingInfo.checkOutDate
    var payment by HotelBookingInfo.payment
    val selectedRooms by RoomBookingEntity referrersOn RoomBooking.bookingId
}