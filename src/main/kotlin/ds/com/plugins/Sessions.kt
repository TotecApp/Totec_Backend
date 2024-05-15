package ds.com.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*

data class UserSession(val username: String)

fun Application.configureSessions(){
    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory())//,directorySessionStorage(File("build/.sessions")))
    }
}

