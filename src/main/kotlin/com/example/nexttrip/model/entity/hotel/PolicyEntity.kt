package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.Policies
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PolicyEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PolicyEntity>(Policies)

    var cancellationPolicy by Policies.cancellation_policy
    var pets by Policies.pets
    var smoking by Policies.smoking
    var hotel by HotelEntity referencedOn  Policies.hotel
}