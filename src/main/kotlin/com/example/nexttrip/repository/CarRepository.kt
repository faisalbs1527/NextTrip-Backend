package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.BookingData
import com.example.nexttrip.model.dto.CarData
import com.example.nexttrip.model.dto.LocationData

interface CarRepository {
    fun getAllCars(): List<CarData>
    fun getCarsByRoute(fromId: Int, destinationId: Int): List<CarData>
    fun addCar(carData: CarData)
    fun getLocations(): List<LocationData>
    fun confirmRide(bookingData: BookingData): BookingData
    fun cancelRide(bookingId: Int)
    fun updateBookingStatus(bookingId: Int, status: String)
    fun addTripRating(bookingId: Int, rating: Double)
}