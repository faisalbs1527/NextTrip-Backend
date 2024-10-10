package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.BookingSeats
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BookingSeatsEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookingSeatsEntity>(BookingSeats)

    var passengerNo by BookingSeats.passengerNo
    var bookingId by FlightBookingEntity referencedOn BookingSeats.bookingId
    var seatId by SeatEntity.optionalReferencedOn(BookingSeats.seatId)
}