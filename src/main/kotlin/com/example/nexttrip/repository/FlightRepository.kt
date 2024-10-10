package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightBookingRequest
import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.dto.hotel.BookingResponseBody

interface FlightRepository {
    fun addFlight(flightDataReceive: FlightDataReceive)
    fun getFlights(): List<FlightDataReceive>
    fun requestFlightBooking(flightBookingRequest: FlightBookingRequest): BookingResponseBody
}