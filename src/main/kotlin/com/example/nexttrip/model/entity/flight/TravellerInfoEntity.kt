package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.TravellerInfo
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TravellerInfoEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TravellerInfoEntity>(TravellerInfo)

    var bookingId by FlightBookingEntity referencedOn TravellerInfo.bookingId
    var type by TravellerInfo.type
    var typeNo by TravellerInfo.typeNo
    var title by TravellerInfo.title
    var firstName by TravellerInfo.firstName
    var lastName by TravellerInfo.lastName
    var dateOfBirth by TravellerInfo.dateOfBirth
}