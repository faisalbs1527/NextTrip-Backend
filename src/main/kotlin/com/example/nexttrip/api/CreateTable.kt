package com.example.nexttrip.api

import com.example.nexttrip.model.tables.car.Bookings
import com.example.nexttrip.model.tables.car.Cars
import com.example.nexttrip.model.tables.car.Location
import com.example.nexttrip.model.tables.car.Routes
import com.example.nexttrip.model.tables.flight.Baggage
import com.example.nexttrip.model.tables.flight.Flights
import com.example.nexttrip.model.tables.flight.Pricing
import com.example.nexttrip.model.tables.flight.Seats
import com.example.nexttrip.model.tables.hotel.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.createTable() {
    transaction {
        SchemaUtils.create(Cars, Location, Routes, Bookings)
        SchemaUtils.create(Hotels, Rooms, Services, Policies, HotelService, HotelBookingInfo, RoomBooking)
        SchemaUtils.create(Flights, Pricing, Baggage, Seats)
    }
}