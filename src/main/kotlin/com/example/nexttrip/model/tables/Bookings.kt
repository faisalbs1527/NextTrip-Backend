package com.example.nexttrip.model.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Bookings : IntIdTable("bookings") {
    val car = reference("car_id",Cars)
    val fromLocation = reference("from_location_id",Location)
    val toLocation = reference("to_location_id",Location)
    val userID = varchar("user_id", 10)
    val status = varchar("status", 20)
    val price = double("price")
    val rating = double("rating")
}