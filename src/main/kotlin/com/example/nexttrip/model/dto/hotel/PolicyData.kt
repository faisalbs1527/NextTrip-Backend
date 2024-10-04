package com.example.nexttrip.model.dto.hotel

import kotlinx.serialization.Serializable

@Serializable
data class PolicyData(
    var cancellation_policy: String,
    var pets: String,
    var smoking: String
)
