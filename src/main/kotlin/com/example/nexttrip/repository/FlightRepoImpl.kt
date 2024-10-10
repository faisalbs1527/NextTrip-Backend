package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.flight.FlightDataReceive
import com.example.nexttrip.model.entity.flight.FlightEntity
import com.example.nexttrip.model.mapper.*
import org.jetbrains.exposed.sql.transactions.transaction

class FlightRepoImpl : FlightRepository {
    override fun addFlight(flightDataReceive: FlightDataReceive) {
        try {
            transaction {
                val flight = flightDataReceive.toFlightEntity()
                flightDataReceive.baggage.toBaggageEntity(flight)
                flightDataReceive.pricing.forEach { it.toPricingEntity(flight) }
                flightDataReceive.seatPlan.forEach { it.toSeatEntity(flight) }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getFlights(): List<FlightDataReceive> {
        return transaction {
            FlightEntity.all().toList().map { it.toFlightDataReceive() }
        }
    }
}