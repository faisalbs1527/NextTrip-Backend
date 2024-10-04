package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.entity.car.RouteEntity.Companion.referrersOn
import com.example.nexttrip.model.tables.hotel.HotelService
import com.example.nexttrip.model.tables.hotel.Hotels
import com.example.nexttrip.model.tables.hotel.Policies
import com.example.nexttrip.model.tables.hotel.Rooms
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class HotelEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HotelEntity>(Hotels)

    var hotelId by Hotels.hotel_id
    var name by Hotels.name
    var location by Hotels.location
    var city by Hotels.city
    var latitude by Hotels.latitude
    var longitude by Hotels.longitude
    var rating by Hotels.rating
    var description by Hotels.description
    var checkInTime by Hotels.check_in_time
    var checkOutTime by Hotels.check_out_time
    var startPriceDiscount by Hotels.start_price_discount
    var startPriceActual by Hotels.start_price_actual
    var imageUrl by Hotels.image_url

    val policies by PolicyEntity referrersOn Policies.hotel
    val services by HotelServiceEntity referrersOn  HotelService.hotel
    val rooms by RoomEntity referrersOn  Rooms.hotel_id
}