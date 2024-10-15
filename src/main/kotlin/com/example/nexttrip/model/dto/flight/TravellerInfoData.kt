package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class TravellerInfoData(
    val type: String,
    val typeNo: Int,
    val title: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val dateOfBirth: String? = null,
)
