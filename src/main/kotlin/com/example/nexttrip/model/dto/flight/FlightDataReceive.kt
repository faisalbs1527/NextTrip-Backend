package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class FlightDataReceive(
    val flightNumber: String,
    val departureAirport: String,
    val arrivalAirport: String,
    val departureTime: String,
    val arrivalTime: String,
    val airline: String,
    val departureGate: String,
    val arrivalGate: String,
    val pricing: List<PricingDataReceive>,
    val seatPlan: List<SeatPlanData>,
    val baggage: BaggageData
)
