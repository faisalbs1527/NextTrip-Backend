package com.example.nexttrip

import com.example.nexttrip.api.createTable
import com.example.nexttrip.api.dbConnection
import com.example.nexttrip.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    dbConnection()
    createTable()
    configureSerialization()
    configureRouting()
}
