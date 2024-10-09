package com.example.nexttrip.model.tables.flight

import org.jetbrains.exposed.dao.id.IntIdTable

object Baggage : IntIdTable("baggage") {
    val flightId = reference("flight_id", Flights)
    val carryOnAllowance = varchar("carry_on_allowance", 50)
    val checkedAllowance = varchar("checked_allowance", 50)
}