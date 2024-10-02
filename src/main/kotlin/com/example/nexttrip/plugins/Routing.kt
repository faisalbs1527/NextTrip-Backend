package com.example.nexttrip.plugins

import com.example.nexttrip.repository.CarRepoImpl
import com.example.nexttrip.route.routeCar
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val carRepository = CarRepoImpl()
    routeCar(carRepository)
}
