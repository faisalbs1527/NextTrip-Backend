package com.example.nexttrip.api

import com.example.nexttrip.model.tables.Bookings
import com.example.nexttrip.model.tables.Cars
import com.example.nexttrip.model.tables.Location
import com.example.nexttrip.model.tables.Routes
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.createTable() {
    transaction {
        SchemaUtils.create(Cars)
        SchemaUtils.create(Location)
        SchemaUtils.create(Routes)
        SchemaUtils.create(Bookings)
    }
}