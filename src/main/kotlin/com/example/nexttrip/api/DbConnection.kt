package com.example.nexttrip.api

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

fun Application.dbConnection() {
    try {
        Database.connect(
            url = "jdbc:mysql://localhost:3306/nextTrip",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "123456"
        )
    } catch (e: Exception) {
        log.error("Database connection failed: ${e.localizedMessage}")
        throw e
    }
}