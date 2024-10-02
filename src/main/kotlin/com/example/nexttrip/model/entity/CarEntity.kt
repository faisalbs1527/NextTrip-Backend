package com.example.nexttrip.model.entity

import com.example.nexttrip.model.tables.Cars
import com.example.nexttrip.model.tables.Routes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CarEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<CarEntity>(Cars)

    var carId by Cars.carId
    var latitude by Cars.latitude
    var longitude by Cars.longitude
    var rotation by Cars.rotation
    var carName by Cars.carName
    var model by Cars.model
    var riderName by Cars.riderName
    var fuelType by Cars.fuelType
    var gearType by Cars.gearType
    var color by Cars.color
    var image by Cars.image
    var successfulRides by Cars.successfulRides
    var reviews by Cars.reviews
    var rating by Cars.rating
    val routes by RouteEntity referrersOn Routes.car
}