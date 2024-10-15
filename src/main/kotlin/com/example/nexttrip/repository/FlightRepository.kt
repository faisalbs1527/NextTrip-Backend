package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.*
import com.example.nexttrip.model.dto.hotel.BookingResponseBody

interface FlightRepository {
    fun addFlight(flightDataReceive: FlightDataReceive)
    fun getFlights(): List<FlightDataReceive>
    fun requestFlightBooking(flightBookingRequest: FlightBookingRequest): BookingResponseBody
    fun getAvailableFlightsOneWay(bookingId: Int): List<FlightDetailsResponse>
    fun getAvailableFlightsBothWay(bookingId: Int): List<Pair<FlightDetailsResponse, FlightDetailsResponse>>
    fun selectFlight(flightSelectionRequestBody: FlightSelectionRequestBody): BookingResponseBody
    fun addTravellersInfo(bookingId: Int, travellers: List<TravellerInfoData>): BookingResponseBody
    fun getSeatList(bookingId: Int, returnSeats: Boolean): List<SeatPlanData>
    fun selectSeats(selectSeatRequest: SelectSeatRequest): BookingResponseBody
    fun getBookingDetails(bookingId: Int): FlightBookingResponse
    fun confirmBooking(bookingId: Int): BookingResponseBody
}