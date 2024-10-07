package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.BookingRequestBody
import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.model.dto.hotel.RoomData

interface HotelRepository {
    fun getAllHotels(): List<HotelReceiveData>
    fun addHotel(hotelReceiveData: HotelReceiveData)
    fun getAvailableHotels()
    fun getRooms(hotelId: String):List<RoomData>
    fun requestBooking(bookingRequestBody: BookingRequestBody)
}