package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightDataReceive

interface FlightRepository {
    fun addFlight(flightDataReceive: FlightDataReceive)
    fun getFlights(): List<FlightDataReceive>
}