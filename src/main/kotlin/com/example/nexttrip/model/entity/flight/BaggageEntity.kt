package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.Baggage
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BaggageEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BaggageEntity>(Baggage)

    var flightId by Baggage.flightId
    var carryOnAllowance by Baggage.carryOnAllowance
    var checkedAllowance by Baggage.checkedAllowance
}