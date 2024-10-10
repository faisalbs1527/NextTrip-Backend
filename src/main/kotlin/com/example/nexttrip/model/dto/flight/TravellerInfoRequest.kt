package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class TravellerInfoRequest(
    val type: String,
    val typeNo: Int,
    var title: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirth: String = "",
)
