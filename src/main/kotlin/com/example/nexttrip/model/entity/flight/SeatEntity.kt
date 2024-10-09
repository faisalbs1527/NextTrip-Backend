package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.Seats
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SeatEntity(id: EntityID<Int>) : IntEntity(id = id) {
    companion object : IntEntityClass<SeatEntity>(Seats)

    var flightId by Seats.flightId
    var seatNumber by Seats.seatNumber
    var classType by Seats.classType
    var status by Seats.status
}