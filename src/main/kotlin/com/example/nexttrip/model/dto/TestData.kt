package com.example.nexttrip.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class TestData(
    val id: Int,
    val name: String
)
