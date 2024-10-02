package com.example.nexttrip.model.entity.car

import com.example.nexttrip.model.tables.car.Routes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RouteEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RouteEntity>(Routes)
    var car by CarEntity referencedOn Routes.car
    var fromLocation by LocationEntity referencedOn Routes.fromLocation
    var toLocation by LocationEntity referencedOn Routes.toLocation
}