package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.*
import com.example.nexttrip.model.entity.hotel.AvailableHotelData

interface HotelRepository {
    fun getAllHotels(): List<HotelReceiveData>
    fun addHotel(hotelReceiveData: HotelReceiveData)
    fun getAvailableHotels(bookingId: Int): List<AvailableHotelData>
    fun getHotelDetails(bookingId: Int, hotelId: String): ResponseHotelDetails
    fun requestBooking(bookingRequestBody: BookingRequestBody): Int
    fun getAvailableRooms(bookingId: Int, roomNo: Int): List<RoomData>
    fun selectRoom(bookingId: Int, roomNo: Int, selectedRoomId: String)
    fun getBookingDetails(bookingId: Int): ResponseBookingInfo
}