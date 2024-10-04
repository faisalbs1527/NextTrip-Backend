package com.example.nexttrip.repository

import com.example.nexttrip.model.dto.hotel.HotelReceiveData
import com.example.nexttrip.model.dto.hotel.RoomData
import com.example.nexttrip.model.entity.hotel.HotelEntity
import com.example.nexttrip.model.entity.hotel.HotelServiceEntity
import com.example.nexttrip.model.entity.hotel.ServiceEntity
import com.example.nexttrip.model.tables.hotel.Services
import com.example.nexttrip.model.toHotelEntity
import com.example.nexttrip.model.toHotelReceiveDTO
import com.example.nexttrip.model.toPolicyEntity
import com.example.nexttrip.model.toRoomEntity
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
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

    override fun getAvailableHotels() {
        TODO("Not yet implemented")
    }

    override fun getRooms(hotelId: String): List<RoomData> {
        TODO("Not yet implemented")
    }
}