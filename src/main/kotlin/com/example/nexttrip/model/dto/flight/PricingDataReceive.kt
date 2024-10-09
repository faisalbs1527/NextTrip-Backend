package com.example.nexttrip.model.dto.flight

import kotlinx.serialization.Serializable

@Serializable
data class PricingDataReceive(
    val classType: String,
    val price: Double,
    val currency: String
)
