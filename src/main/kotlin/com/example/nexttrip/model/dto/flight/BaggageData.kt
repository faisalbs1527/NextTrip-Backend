package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class BaggageData(
    val carryOnAllowance: String,
    val checkedAllowance: String
)
