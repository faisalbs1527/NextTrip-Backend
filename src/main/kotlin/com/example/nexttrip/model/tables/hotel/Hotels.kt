package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object Hotels : IntIdTable("hotels") {
    val hotel_id = varchar("hotel_id", 10).uniqueIndex()
    val name = varchar("name", 255)
    val location = varchar("location", 255)
    val city = varchar("city", 255)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val rating = double("rating")
    val description = text("description")
    val check_in_time = varchar("check_in_time", 20)
    val check_out_time = varchar("check_out_time", 20)
    val start_price_discount = integer("start_price_discount")
    val start_price_actual = integer("start_price_actual")
    val image_url = varchar("image_url", 255)

}