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

fun Route.userRouting() {
    route("/users") {
        showUsers()
    }
    route("/signUp"){
        signUp()
    }

    route("/login"){
        login()
    }

    route("/logout"){
        logout()
    }

    route("/isLogged"){
        isLogged()
    }
}

fun Route.showUsers(){
    get{
        if(userStorage.isNotEmpty()){
            call.respond(userStorage)
        }
        else{
            call.respondText("No users found", status = HttpStatusCode.OK)
        }
    }
}

fun Route.signUp(){
    post{
        val user = call.receive<User>()
        userStorage.add(user)
        call.respondText("User added successfully", status = HttpStatusCode.Created)
    }
}

fun Route.login(){
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

        val user = userStorage.find{ it.username == username } ?: return@get call.respondText(
            "Username or password incorrect",
            status = HttpStatusCode.NotFound
        )
        if (user.password == password) {
            call.sessions.set(UserSession(username = user.username, loggedIn = true))
            call.respondText("Success")
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
//            val userData = "{username: ${userSession.username}, 'loggedIn': true}"
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

fun Route.logout() {
    get {
        call.sessions.clear<UserSession>()
        call.respondText("Logout successful", status = HttpStatusCode.OK)
    }
}


