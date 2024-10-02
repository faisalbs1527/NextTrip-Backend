package com.example.nexttrip.model.entity.hotel

import com.example.nexttrip.model.tables.hotel.Services
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ServiceEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ServiceEntity>(Services)

    var service by Services.service
}