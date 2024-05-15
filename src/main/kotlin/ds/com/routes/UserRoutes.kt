package ds.com.routes

import ds.com.models.*
import io.ktor.http.*
import ds.com.plugins.UserSession
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing. *
import io.ktor.server.sessions.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder

var logged = false

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
        logged = true
        val user = call.receive<User>()
        userStorage.add(user)
        call.respondText("User added successfully", status = HttpStatusCode.Created)
    }
}

fun Route.login(){
    get {
        if(logged){
            call.respondText("User already logged in")
        }
        val username = URLDecoder.decode(call.parameters["username"], "UTF-8") ?: return@get call.respondText(
            "Missing username",
            status = HttpStatusCode.BadRequest
        )
        val password = URLDecoder.decode(call.parameters["password"], "UTF-8") ?: return@get call.respondText(
            "Missing password",
            status = HttpStatusCode.BadRequest
        )
        val user = userStorage.find{ it.username == username } ?: return@get call.respondText(
            "Username or password incorrect",
            status = HttpStatusCode.NotFound
        )
        if (user.password == password) {
            call.sessions.set(UserSession(username = user.username))
            logged = true
            call.respondText("Login successful!")
        }
        else{
            call.respondText("Username or password incorrect")
        }
    }
}

fun Route.logout() {
    get {
        logged = false
        call.sessions.clear<UserSession>()
        call.respondText("Logout successful", status = HttpStatusCode.OK)
    }
}

fun Route.isLogged(){
    get{
        if(logged){
            val userSession = call.sessions.get<UserSession>()
            val userData = mapOf("username" to userSession?.username)
            call.respond(HttpStatusCode.OK, Json.encodeToString(userData))
        }
        else{
            call.respondText("Logged out")
        }
    }
}
