package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object Pricing : IntIdTable("priceinfo") {
    val flightId = reference("flight_id", Flights)
    val classType = varchar("class_type", 20)
    val price = double("price")
    val currency = varchar("currency", 5)

    init {
        uniqueIndex(flightId, classType)
    }
}