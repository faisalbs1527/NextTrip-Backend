package com.example.nexttrip.repository

import com.example.nexttrip.exception.DuplicateEntryException
import com.example.nexttrip.model.dto.hotel.BookingRequestBody
import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.model.dto.hotel.RoomData
import com.example.nexttrip.model.entity.hotel.*
import com.example.nexttrip.model.tables.hotel.HotelBookingInfo
import com.example.nexttrip.model.tables.hotel.Services
import com.example.nexttrip.model.toHotelEntity
import com.example.nexttrip.model.toHotelReceiveDTO
import com.example.nexttrip.model.toPolicyEntity
import com.example.nexttrip.model.toRoomEntity
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLIntegrityConstraintViolationException

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

    override fun getAvailableHotels() {
        TODO("Not yet implemented")
    }

    override fun getRooms(hotelId: String): List<RoomData> {
        TODO("Not yet implemented")
    }

    override fun requestBooking(bookingRequestBody: BookingRequestBody) {
        try {
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
            }
        }catch (ex: Exception) {
            ex.printStackTrace()
            throw ex
        }
    }
}