package ds.com
import ds.com.models.*

import ds.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable

@Serializable
data class UserSession(val username: String?, val loggedIn: Boolean)

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module(testing : Boolean = false) {
    configureDatabases()
    val userRepository = PostgresUserRepository()
    val recipeRepository = PostgresRecipeRepository()
    val favoriteRepository = PostgresFavoritesRepository()
    val tagRepository = PostgresTagRepository()
    val tagRelationRepository = PostgresTagRelationRepository()
    configureSerialization(userRepository, recipeRepository, favoriteRepository, tagRepository, tagRelationRepository)
    configureCors()
    install(Sessions){
        cookie<UserSession>("user_session", SessionStorageMemory()){
            cookie.secure = true
            cookie.path = "/"
            cookie.extensions["SameSite"] = "None"
        }
    }
}