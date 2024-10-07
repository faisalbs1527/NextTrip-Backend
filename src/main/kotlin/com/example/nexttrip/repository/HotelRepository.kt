package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.BookingRequestBody
import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.model.dto.hotel.ResponseHotelDetails
import com.example.nexttrip.model.dto.hotel.RoomData
import com.example.nexttrip.model.entity.hotel.AvailableHotelData

interface HotelRepository {
    fun getAllHotels(): List<HotelReceiveData>
    fun addHotel(hotelReceiveData: HotelReceiveData)
    fun getAvailableHotels(bookingId: Int): List<AvailableHotelData>
    fun getHotelDetails(hotelId: String): ResponseHotelDetails
    fun getRooms(hotelId: String): List<RoomData>
    fun requestBooking(bookingRequestBody: BookingRequestBody): Int
}