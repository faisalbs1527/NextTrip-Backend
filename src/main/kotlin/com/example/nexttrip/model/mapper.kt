package com.example.nexttrip.model

import com.example.nexttrip.model.dto.car.BookingData
import com.example.nexttrip.model.dto.car.CarData
import com.example.nexttrip.model.dto.car.LocationData
import com.example.nexttrip.model.dto.car.RouteData
import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.model.dto.hotel.PolicyData
import com.example.nexttrip.model.dto.hotel.RoomData
import com.example.nexttrip.model.entity.car.BookingEntity
import com.example.nexttrip.model.entity.car.CarEntity
import com.example.nexttrip.model.entity.car.LocationEntity
import com.example.nexttrip.model.entity.car.RouteEntity
import com.example.nexttrip.model.entity.hotel.HotelEntity
import com.example.nexttrip.model.entity.hotel.PolicyEntity
import com.example.nexttrip.model.entity.hotel.RoomEntity
import com.example.nexttrip.model.entity.hotel.ServiceEntity
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID

fun CarEntity.toCarDTO() = CarData(carId = carId,
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
    availableRoutes = routes.toList().map { it.toRouteDTO() })

fun RouteEntity.toRouteDTO() = RouteData(
    fromLocation = LocationData(
        id = fromLocation.id.value,
        name = fromLocation.name,
        latitude = fromLocation.latitude,
        longitude = fromLocation.longitude
    ), toLocation = LocationData(
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
    id = id.value, name = name, latitude = latitude, longitude = longitude
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

fun HotelReceiveData.toHotelEntity(actualPrice: Int, discountPrice: Int) = HotelEntity.new {
    hotelId = this@toHotelEntity.hotel_id
    name = this@toHotelEntity.name
    location = this@toHotelEntity.location
    city = this@toHotelEntity.city
    latitude = this@toHotelEntity.latitude
    longitude = this@toHotelEntity.longitude
    rating = this@toHotelEntity.rating
    description = this@toHotelEntity.description
    checkInTime = this@toHotelEntity.check_in_time
    checkOutTime = this@toHotelEntity.check_out_time
    startPriceActual = actualPrice
    startPriceDiscount = discountPrice
    imageUrl = this@toHotelEntity.image_url
}

fun HotelEntity.toHotelReceiveDTO() = HotelReceiveData(
    hotel_id = hotelId,
    name = name,
    location = location,
    city = city,
    latitude = latitude,
    longitude = longitude,
    rating = rating,
    description = description,
    check_in_time = checkInTime,
    check_out_time = checkOutTime,
    image_url = imageUrl,
    policies = policies.toList().first().toPolicyDTO(),
    complimentary_services = services.toList().map { it.service.serviceName },
    rooms = rooms.toList().map { it.toRoomDTO() }
)

fun RoomData.toRoomEntity(hotelEntity: HotelEntity) = RoomEntity.new {
    roomNo = this@toRoomEntity.room_id
    hotel = hotelEntity
    roomType = this@toRoomEntity.room_type
    capacity = this@toRoomEntity.capacity
    bedType = this@toRoomEntity.bed_type
    numberOfBeds = this@toRoomEntity.number_of_beds
    acStatus = this@toRoomEntity.ac_status
    availability = this@toRoomEntity.availability
    actualPrice = this@toRoomEntity.actual_price
    discountPrice = this@toRoomEntity.discount_price
    imageUrl = this@toRoomEntity.image_url
}

fun RoomEntity.toRoomDTO() = RoomData(
    room_id = roomNo,
    room_type = roomType,
    capacity = capacity,
    bed_type = bedType,
    number_of_beds = numberOfBeds,
    ac_status = acStatus,
    availability = availability,
    actual_price = actualPrice,
    discount_price = discountPrice,
    image_url = imageUrl
)

fun PolicyData.toPolicyEntity(hotelEntity: HotelEntity) = PolicyEntity.new {
    hotel = hotelEntity
    cancellationPolicy = this@toPolicyEntity.cancellation_policy
    pets = this@toPolicyEntity.pets
    smoking = this@toPolicyEntity.smoking
}

fun PolicyEntity.toPolicyDTO() = PolicyData(
    cancellation_policy = cancellationPolicy,
    pets = pets,
    smoking = smoking
)
