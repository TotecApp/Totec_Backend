package com.mac

import com.mac.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureRouting()
}
