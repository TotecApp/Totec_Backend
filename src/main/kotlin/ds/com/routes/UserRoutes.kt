package ds.com.routes
import io.ktor.server.application.*
import ds.com.models.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing. *
import io.ktor.server.sessions.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import ds.com.UserSession
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import ds.com.models.UserRepository
import io.ktor.serialization.*


fun Route.userRouting(repository: UserRepository){
    route("/users") {
        showUsers(repository)
    }
    route("/signUp"){
        signUp(repository)
    }

    route("/login"){
        login(repository)
    }

    route("/logout"){
        logout(repository)
    }

    route("/isLogged"){
        isLogged()
    }
}

fun Route.showUsers(repository: UserRepository){
    get{
        val users = repository.allUsers()
        call.respond(users)
    }
}

fun Route.signUp(repository: UserRepository){
    post{
        try{
            val user = call.receive<UserDTO>()
            repository.addNewUser(user)
            call.respond(HttpStatusCode.NoContent)
        } catch (ex: IllegalStateException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        } catch (ex: JsonConvertException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.login(repository: UserRepository){
    get{
        call.application.log.info("Retrieving user session")
        val userSession = call.sessions.get<UserSession>()
        call.application.log.info("User session retrieved")
        if(userSession != null){
            call.respondText("User already logged in")
            return@get
        }

        val username = call.parameters["username"]?.let { URLDecoder.decode(it, "UTF-8") }
            ?: return@get call.respondText("Missing username", status = HttpStatusCode.BadRequest)
        val password = call.parameters["password"]?.let { URLDecoder.decode(it, "UTF-8") }
            ?: return@get call.respondText("Missing password", status = HttpStatusCode.BadRequest)

        call.application.log.info("Attempting to login with username: $username")
        val user = repository.user(username)
        if(user != null){
            if(user.password == password){
                call.sessions.set(UserSession(username = user.username, loggedIn = true))
                call.respondText("Login successful")
            }
            else{
                call.respondText("Username or password incorrect")
            }
        }
        else{
            call.respondText("Username or password incorrect")
        }
    }
}

fun Route.isLogged(){
    get{
        call.application.log.info("Trying to retrieve username")
        val userSession = call.sessions.get<UserSession>()
        call.application.log.info("Checking for existing session: $userSession")
        if(userSession != null){
            call.application.log.info("Session retrieved for username: ${userSession.username}")
            val userData = UserSession(username = userSession.username, loggedIn = true)
            call.respond(HttpStatusCode.OK, Json.encodeToString(userData))
        }
        else{
            call.application.log.info("No session found")
            val notLoggedIn = UserSession(username = null, loggedIn = false)
            call.respond(HttpStatusCode.OK, Json.encodeToString(notLoggedIn))
        }
    }
}

fun Route.logout(repository: UserRepository) {
    get {
        call.sessions.clear<UserSession>()
        call.respondText("Logout successful", status = HttpStatusCode.OK)
    }
}


