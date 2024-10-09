package com.example.nexttrip.model.entity.flight

import com.example.nexttrip.model.tables.flight.Pricing
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PricingEntity (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PricingEntity>(Pricing)

    var flightId by Pricing.flightId
    var classType by Pricing.classType
    var price by Pricing.price
    var currency by Pricing.currency
}