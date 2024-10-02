package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object Policies : IntIdTable("policies") {
    val cancellation_policy = varchar("cancellation_policy", 255)
    val pets = varchar("pets", 255)
    val smoking = varchar("smoking", 255)
    val hotel = reference("hotel_id",Hotels)

    init {
        uniqueIndex(cancellation_policy, pets, smoking)
    }
}