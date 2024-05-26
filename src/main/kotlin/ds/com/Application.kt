package ds.com

import ds.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.sessions.*
import io.ktor.server.routing.*
import ds.com.routes.userRouting
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val username: String?, val loggedIn: Boolean)

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureCors()
    install(Sessions){
        cookie<UserSession>("user_session", SessionStorageMemory()){
            cookie.secure = true
            cookie.path = "/"
            cookie.extensions["SameSite"] = "None"
        }
    }

    install(ContentNegotiation) {
        json(Json{
            prettyPrint = true
            isLenient = true
        })
    }
}

