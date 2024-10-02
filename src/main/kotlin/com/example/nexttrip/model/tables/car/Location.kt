package com.example.nexttrip.model.tables.car

import org.jetbrains.exposed.dao.id.IntIdTable

object Location : IntIdTable("location") {
    val name = varchar("name", 100)
    val latitude = double("latitude")
    val longitude = double("longitude")

    init {
        uniqueIndex(latitude, longitude)
    }
}