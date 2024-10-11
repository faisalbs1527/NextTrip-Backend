package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class FlightDetailsResponse(
    val airline: String,
    val arrivalAirport: String,
    val arrivalTime: String,
    val classType: String,
    val currency: String,
    val departureAirport: String,
    val departureTime: String,
    val flightNumber: String,
    val departureGate: String,
    val arrivalGate: String,
    val price: Double,
    val duration: String,
    val baggage: BaggageData,
    val stop: String = "Non-Stops"
)
