package com.example.nexttrip.model.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Cars : IntIdTable("cars") {
    val carId = varchar("car_id", 10)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val rotation = integer("rotation")
    val carName = varchar("car_name", 50)
    val model = varchar("model", 10)
    val riderName = varchar("rider_name", 50)
    val fuelType = varchar("fuel_type", 20)
    val gearType = varchar("gear_type", 20)
    val color = varchar("color", 20)
    val image = varchar("image", 255)
    val successfulRides = integer("successful_rides")
    val reviews = integer("reviews")
    val rating = double("rating")

    init {
        uniqueIndex(carId)
    }
}