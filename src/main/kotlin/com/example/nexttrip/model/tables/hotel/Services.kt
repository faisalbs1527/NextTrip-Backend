package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object Services : IntIdTable("services") {
    val service = varchar("service", 255)
}