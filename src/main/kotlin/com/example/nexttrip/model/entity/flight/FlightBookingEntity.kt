package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.entity.flight.FlightBookingEntity.Companion.referrersOn
import com.example.nexttrip.model.tables.flight.BookingSeats
import com.example.nexttrip.model.tables.flight.FlightBookingInfo
import com.example.nexttrip.model.tables.flight.TravellerInfo
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FlightBookingEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FlightBookingEntity>(FlightBookingInfo)

    var departureFlight by FlightBookingInfo.departureFlight
    var returnFlight by FlightBookingInfo.returnFlight
    var userID by FlightBookingInfo.userID
    var status by FlightBookingInfo.status
    var departureAirport by FlightBookingInfo.departureAirport
    var arrivalAirport by FlightBookingInfo.arrivalAirport
    var departureDate by FlightBookingInfo.departureDate
    var returnDate by FlightBookingInfo.returnDate
    var classType by FlightBookingInfo.classType
    var flightType by FlightBookingInfo.flightType
    var payment by FlightBookingInfo.payment

    val travellers by TravellerInfoEntity referrersOn TravellerInfo.bookingId
    val selectedSeats by BookingSeatsEntity referrersOn BookingSeats.bookingId
}