package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class FlightBookingRequest(
    val userId: String,
    val status: String = "Pending",
    val departureAirport: String,
    val arrivalAirport: String,
    val travelDate: String,
    val classType: String,
    val flightType: String,
    val travellers: List<TravellerInfoRequest>
)
