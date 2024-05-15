package ds.com.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.http.HttpHeaders

fun Application.configureCors(){
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader("X-Requested-With")
        allowHeader(HttpHeaders.Origin)
        allowCredentials = true
    }
}