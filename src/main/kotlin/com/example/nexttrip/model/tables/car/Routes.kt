package com.example.nexttrip.model.tables.car

import org.jetbrains.exposed.dao.id.IntIdTable

object Routes: IntIdTable("routes") {
    val car = reference("car_id", Cars)
    val fromLocation = reference("from_location_id", Location)
    val toLocation = reference("to_location_id", Location)
}