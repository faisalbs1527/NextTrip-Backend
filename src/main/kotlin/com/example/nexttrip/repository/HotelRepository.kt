package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.HotelReceiveData

interface HotelRepository {
    fun getAllHotels(): List<HotelReceiveData>
    fun addHotel(hotelReceiveData: HotelReceiveData)
    fun getAvailableHotels()
    fun getRooms()
}