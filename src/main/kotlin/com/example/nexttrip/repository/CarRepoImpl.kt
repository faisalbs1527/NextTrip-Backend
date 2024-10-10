package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.car.BookingData
import com.example.nexttrip.model.dto.car.CarData
import com.example.nexttrip.model.dto.car.LocationData
import com.example.nexttrip.model.entity.car.BookingEntity
import com.example.nexttrip.model.entity.car.CarEntity
import com.example.nexttrip.model.entity.car.LocationEntity
import com.example.nexttrip.model.entity.car.RouteEntity
import com.example.nexttrip.model.mapper.toBookingDTO
import com.example.nexttrip.model.mapper.toCarDTO
import com.example.nexttrip.model.mapper.toCarEntity
import com.example.nexttrip.model.mapper.toLocationDTO
import com.example.nexttrip.model.tables.car.Cars
import com.example.nexttrip.model.tables.car.Location
import com.example.nexttrip.model.tables.car.Routes
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class CarRepoImpl : CarRepository {
    override fun getAllCars(): List<CarData> {
        return transaction {
            CarEntity.all().toList().map { it.toCarDTO() }
        }
    }

    override fun getCarsByRoute(fromId: Int, destinationId: Int): List<CarData> {
        return transaction {
            val availableCars = RouteEntity.find {
                (Routes.fromLocation eq fromId) and (Routes.toLocation eq destinationId)
            }.map { it.car }

            availableCars.map { it.toCarDTO() }
        }
    }

    override fun addCar(carData: CarData) {
        return try {
            transaction {
                val car = carData.toCarEntity()
                carData.availableRoutes.forEach { routeData ->
                    val fromLocation = LocationEntity.find {
                        (Location.latitude eq routeData.fromLocation.latitude) and (Location.longitude eq routeData.fromLocation.longitude)
                    }.firstOrNull() ?: LocationEntity.new {
                        name = routeData.fromLocation.name
                        latitude = routeData.fromLocation.latitude
                        longitude = routeData.fromLocation.longitude
                    }

                    val toLocation = LocationEntity.find {
                        (Location.latitude eq routeData.toLocation.latitude) and (Location.longitude eq routeData.toLocation.longitude)
                    }.firstOrNull() ?: LocationEntity.new {
                        name = routeData.toLocation.name
                        latitude = routeData.toLocation.latitude
                        longitude = routeData.toLocation.longitude
                    }

                    RouteEntity.new {
                        this.car = car
                        this.fromLocation = fromLocation
                        this.toLocation = toLocation
                    }
                }
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun getLocations(): List<LocationData> {
        return transaction {
            LocationEntity.all().toList().map { it.toLocationDTO() }
        }
    }

    override fun confirmRide(bookingData: BookingData): BookingData {
        return try {
            transaction {
                val bookedCar = CarEntity.find {
                    Cars.carId eq bookingData.carId
                }.first()
                val fromLoc = LocationEntity.find {
                    Location.id eq bookingData.fromId
                }.first()
                val toLoc = LocationEntity.find {
                    Location.id eq bookingData.toId
                }.first()
                BookingEntity.new {
                    this.car = bookedCar
                    this.fromLocation = fromLoc
                    this.toLocation = toLoc
                    this.userId = bookingData.userId
                    this.status = bookingData.status
                    this.price = bookingData.price
                    this.rating = bookingData.rating
                }.toBookingDTO()
            }

        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun cancelRide(bookingId: Int) {
        transaction {
            val booking = BookingEntity.findById(bookingId)

            if (booking != null) {
                booking.delete()
            } else {
                throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            }
        }
    }

    override fun updateBookingStatus(bookingId: Int, status: String) {
        transaction {
            val booking = BookingEntity.findById(bookingId)
            if (booking != null) {
                booking.status = status
            } else {
                throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            }
        }
    }

    override fun addTripRating(bookingId: Int, rating: Double) {
        transaction {
            val booking = BookingEntity.findById(bookingId)
            if (booking != null) {
                booking.rating = rating
            } else {
                throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            }
        }
    }
}