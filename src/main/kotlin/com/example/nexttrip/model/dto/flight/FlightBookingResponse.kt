package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class FlightBookingResponse(
    val departureFlight: FlightDetailsResponse,
    val returnFlight: FlightDetailsResponse? = null,
    val userId: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureDate: String,
    val returnDate: String? = null,
    val classType: String,
    val flightType: String,
    val payment: Int,
    val travellers: List<TravellerInfoData>,
    val selectedSeatsDeparture: String,
    val selectedSeatsReturn: String? = null,
)
