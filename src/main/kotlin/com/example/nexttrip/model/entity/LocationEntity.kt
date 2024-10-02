package com.example.nexttrip.model.entity

import com.example.nexttrip.model.tables.Location
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class LocationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<LocationEntity>(Location)

    var name by Location.name
    var latitude by Location.latitude
    var longitude by Location.longitude
}