package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightBookingRequest
import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.dto.flight.FlightDetailsResponse
import com.example.nexttrip.model.dto.hotel.BookingResponseBody

interface FlightRepository {
    fun addFlight(flightDataReceive: FlightDataReceive)
    fun getFlights(): List<FlightDataReceive>
    fun requestFlightBooking(flightBookingRequest: FlightBookingRequest): BookingResponseBody
    fun getAvailableFlightsOneWay(bookingId: Int): List<FlightDetailsResponse>
    fun getAvailableFlightsBothWay(bookingId: Int): List<Pair<FlightDetailsResponse, FlightDetailsResponse>>
    fun addTravellersInfo(): BookingResponseBody
    fun getAvailableSeats()
    fun selectSeats(): BookingResponseBody
    fun getBookingDetails()
    fun confirmBooking(): BookingResponseBody
}