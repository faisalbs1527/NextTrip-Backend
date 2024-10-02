package com.example.nexttrip.model.entity

import com.example.nexttrip.model.tables.Bookings
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BookingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookingEntity>(Bookings)

    var car by CarEntity referencedOn Bookings.car
    var fromLocation by LocationEntity referencedOn Bookings.fromLocation
    var toLocation by LocationEntity referencedOn Bookings.toLocation
    var userId by Bookings.userID
    var status by Bookings.status
    var price by Bookings.price
    var rating by Bookings.rating
}