package com.example.nexttrip.model.tables.hotel

import org.jetbrains.exposed.dao.id.IntIdTable

object Rooms : IntIdTable("rooms") {
    val room_no = varchar("room_id",20)
    val hotel_id = reference("hotel_id", Hotels)
    val room_type = varchar("room_type", 255)
    val capacity = integer("capacity")
    val bed_type = varchar("bed_type", 255)
    val number_of_beds = integer("number_of_beds")
    val ac_status = varchar("ac_status", 10)
    val availability = bool("availability")
    val actual_price = integer("actual_price")
    val discount_price = integer("discount_price")
    val image_url = varchar("image_url", 255)

    init {
        uniqueIndex(room_no, hotel_id)
    }
}