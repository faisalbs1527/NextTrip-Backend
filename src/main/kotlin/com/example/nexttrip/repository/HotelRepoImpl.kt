package com.example.nexttrip.repository

import com.example.nexttrip.exception.DuplicateEntryException
import com.example.nexttrip.model.*
import com.example.nexttrip.model.dto.car.BookingData
import com.example.nexttrip.model.dto.hotel.*
import com.example.nexttrip.model.entity.hotel.*
import com.example.nexttrip.model.tables.hotel.HotelBookingInfo
import com.example.nexttrip.model.tables.hotel.Hotels
import com.example.nexttrip.model.tables.hotel.RoomBooking
import com.example.nexttrip.model.tables.hotel.Services
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class HotelRepoImpl : HotelRepository {
    override fun getAllHotels(): List<HotelReceiveData> {
        return transaction {
            HotelEntity.all().toList().map { it.toHotelReceiveDTO() }
        }
    }

    override fun addHotel(hotelReceiveData: HotelReceiveData) {
        try {
            transaction {
                addLogger(StdOutSqlLogger)
                var startPriceActual = Int.MAX_VALUE
                var startPriceDiscount = Int.MAX_VALUE
                hotelReceiveData.rooms.forEach {
                    if (it.actual_price < startPriceActual && it.discount_price < startPriceDiscount) {
                        startPriceActual = it.actual_price
                        startPriceDiscount = it.discount_price
                    }
                }
                val hotel = hotelReceiveData.toHotelEntity(startPriceActual, startPriceDiscount)
                hotelReceiveData.rooms.forEach {
                    it.toRoomEntity(hotel)
                }
                hotelReceiveData.policies.toPolicyEntity(hotel)
                hotelReceiveData.complimentary_services.forEach { service ->
                    val serviceEntity = ServiceEntity.find {
                        Services.service eq service
                    }.firstOrNull() ?: ServiceEntity.new {
                        serviceName = service
                    }
                    HotelServiceEntity.new {
                        this.hotel = hotel
                        this.service = serviceEntity
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getAvailableHotels(bookingId: Int): List<AvailableHotelData> {
        return transaction {
            HotelBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")

            val hotels = HotelEntity.all().toList()
            val roomInfo = RoomBookingEntity.find {
                RoomBooking.bookingId eq bookingId
            }

            val availableHotels = hotels.filter { hotelEntity ->
                checkAvailability(hotelEntity.rooms.toList(), roomInfo.toList())
            }.map { it.toAvailableHotelData() }
            availableHotels
        }
    }

    override fun getHotelDetails(bookingId: Int, hotelId: String): ResponseHotelDetails {
        return transaction {
            val hotel = HotelEntity.find {
                Hotels.hotel_id eq hotelId
            }.firstOrNull()
            if (hotel == null) {
                throw NoSuchElementException("Hotel with ID $hotelId does not exist.")
            }
            val hotelBookingInfo = HotelBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            hotelBookingInfo.hotel = hotel.id

            hotel.toHotelResponse()
        }
    }

    override fun requestBooking(bookingRequestBody: BookingRequestBody): Int {
        return try {
            transaction {

                val existingBooking = HotelBookingEntity.find {
                    (HotelBookingInfo.userID eq bookingRequestBody.userId) and
                            (HotelBookingInfo.checkInDate eq bookingRequestBody.checkIn) and
                            (HotelBookingInfo.checkOutDate eq bookingRequestBody.checkOut) and
                            (HotelBookingInfo.location eq bookingRequestBody.location) and
                            (HotelBookingInfo.status eq bookingRequestBody.status)
                }.firstOrNull()

                if (existingBooking != null) {
                    throw DuplicateEntryException("A booking already exists with the same details.")
                }
                val bookingInfo = HotelBookingEntity.new {
                    userID = bookingRequestBody.userId
                    checkInDate = bookingRequestBody.checkIn
                    checkOutDate = bookingRequestBody.checkOut
                    location = bookingRequestBody.location
                    status = bookingRequestBody.status
                }

                bookingRequestBody.rooms.forEach {
                    RoomBookingEntity.new {
                        bookingId = bookingInfo
                        roomNo = it.roomNo
                        adult = it.adult
                        child = it.child
                    }
                }
                bookingInfo.id.value
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }

    override fun getAvailableRooms(bookingId: Int, roomNo: Int): List<RoomData> {
        return transaction {
            val hotelBookingInfo = HotelBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            val roomInfo = hotelBookingInfo.selectedRooms.find { it.roomNo == roomNo } ?: throw NotFoundException()
            val hotelRooms =
                hotelBookingInfo.hotel?.let { HotelEntity.findById(it.value)?.rooms } ?: throw NotFoundException()

            val availableRooms = hotelRooms.filter {
                it.availability && it.capacity >= roomInfo.adult
            }

            availableRooms.map { it.toRoomDTO() }
        }
    }

    override fun selectRoom(bookingId: Int, roomNo: Int, selectedRoomId: String) {
        transaction {
            val hotelBookingInfo = HotelBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")

            val selectedRoom = hotelBookingInfo.hotel?.let { HotelEntity.findById(it) }?.rooms?.find { roomEntity ->
                roomEntity.roomNo == selectedRoomId
            } ?: throw NotFoundException()
            val roomInfo = hotelBookingInfo.selectedRooms.find { it.roomNo == roomNo } ?: throw NotFoundException()
            roomInfo.selectedRoom = selectedRoom
            selectedRoom.availability = false
        }
    }

    override fun getBookingDetails(bookingId: Int): ResponseBookingInfo {
        return transaction {
            val bookingDetails = HotelBookingEntity.findById(bookingId)
                ?: throw NoSuchElementException("Booking with ID $bookingId does not exist.")
            val selectedHotel = bookingDetails.hotel?.let { HotelEntity.findById(it) }
                ?: throw NotFoundException()
            val response = bookingDetails.toHotelBookingDTO(selectedHotel)
            bookingDetails.payment = response.paymentActual.toInt()
            response
        }
    }
}

private fun checkAvailability(hotelRooms: List<RoomEntity>, bookingRooms: List<RoomBookingEntity>): Boolean {
    for (roomX in bookingRooms) {
        var isAvailable = false
        for (roomY in hotelRooms) {
            if (roomX.adult <= roomY.capacity && roomY.availability) {
                isAvailable = true
                break
            }
        }
        if (!isAvailable) return false
    }
    return true
}