package com.example.nexttrip.model

import com.example.nexttrip.model.dto.BookingData
import com.example.nexttrip.model.dto.CarData
import com.example.nexttrip.model.dto.LocationData
import com.example.nexttrip.model.dto.RouteData
import com.example.nexttrip.model.entity.BookingEntity
import com.example.nexttrip.model.entity.CarEntity
import com.example.nexttrip.model.entity.LocationEntity
import com.example.nexttrip.model.entity.RouteEntity

fun CarEntity.toCarDTO() = CarData(
    carId = carId,
    latitude = latitude,
    longitude = longitude,
    rotation = rotation,
    carName = carName,
    model = model,
    riderName = riderName,
    fuelType = fuelType,
    gearType = gearType,
    color = color,
    image = image,
    successfulRides = successfulRides,
    reviews = reviews,
    rating = rating,
    availableRoutes = routes.toList().map { it.toRouteDTO() }
)

fun RouteEntity.toRouteDTO() = RouteData(
    fromLocation = LocationData(
        id = fromLocation.id.value,
        name = fromLocation.name,
        latitude = fromLocation.latitude,
        longitude = fromLocation.longitude
    ),
    toLocation = LocationData(
        id = toLocation.id.value,
        name = toLocation.name,
        latitude = toLocation.latitude,
        longitude = toLocation.longitude
    )
)

fun CarData.toCarEntity() = CarEntity.new {
    carId = this@toCarEntity.carId
    latitude = this@toCarEntity.latitude
    longitude = this@toCarEntity.longitude
    rotation = this@toCarEntity.rotation
    carName = this@toCarEntity.carName
    model = this@toCarEntity.model
    riderName = this@toCarEntity.riderName
    fuelType = this@toCarEntity.fuelType
    gearType = this@toCarEntity.gearType
    color = this@toCarEntity.color
    image = this@toCarEntity.image
    successfulRides = this@toCarEntity.successfulRides
    reviews = this@toCarEntity.reviews
    rating = this@toCarEntity.rating
}

fun RouteData.toRouteEntity() = RouteEntity.new {
    fromLocation = LocationEntity.new {
        name = this@toRouteEntity.fromLocation.name
        latitude = this@toRouteEntity.fromLocation.latitude
        longitude = this@toRouteEntity.fromLocation.longitude
    }

    toLocation = LocationEntity.new {
        name = this@toRouteEntity.toLocation.name
        latitude = this@toRouteEntity.toLocation.latitude
        longitude = this@toRouteEntity.toLocation.longitude
    }
}

fun LocationEntity.toLocationDTO() = LocationData(
    id = id.value,
    name = name,
    latitude = latitude,
    longitude = longitude
)

fun BookingEntity.toBookingDTO() = BookingData(
    bookingId = id.value,
    carId = car.carId,
    userId = userId,
    fromId = fromLocation.id.value,
    toId = toLocation.id.value,
    status = status,
    price = price,
    rating = rating
)
