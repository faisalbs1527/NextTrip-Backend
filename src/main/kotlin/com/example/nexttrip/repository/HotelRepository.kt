package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.*
import com.example.nexttrip.model.entity.hotel.AvailableHotelData

interface HotelRepository {
    fun getAllHotels(): List<HotelReceiveData>
    fun addHotel(hotelReceiveData: HotelReceiveData)
    fun getAvailableHotels(bookingId: Int): List<AvailableHotelData>
    fun getHotelDetails(bookingId: Int, hotelId: String): ResponseHotelDetails
    fun requestBooking(bookingRequestBody: BookingRequestBody): BookingResponseBody
    fun getAvailableRooms(bookingId: Int, roomNo: Int): List<RoomData>
    fun selectRoom(bookingId: Int, roomNo: Int, selectedRoomId: String): BookingResponseBody
    fun getBookingDetails(bookingId: Int): ResponseBookingInfo
    fun confirmBooking(bookingId: Int): BookingResponseBody
    fun updateBooking(requestFormHotel: RequestFormHotel): BookingResponseBody
}