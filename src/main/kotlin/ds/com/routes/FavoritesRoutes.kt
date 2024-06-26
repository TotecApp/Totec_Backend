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
import ds.com.models.FavoritesRepository

import io.ktor.serialization.*
import kotlinx.serialization.Serializable


fun Route.favoriteRouting(repository: FavoritesRepository){
    route("/addToFavorites"){
        addFavorite(repository)
    }
}
@Serializable
data class AddFavoriteRequest(val userId: Int?, val recipeId: Int?)

fun Route.addFavorite(repository: FavoritesRepository){
    post{
        val favoriteRequest = call.receive<AddFavoriteRequest>()
        val userId = favoriteRequest.userId
        val recipeId = favoriteRequest.recipeId

        if (userId != null && recipeId != null) {
            println("Adding to favorites: User ID: $userId, Recipe ID: $recipeId")
            val favorite = FavoritesDTO(userId, recipeId)
            repository.addNewFavorite(favorite)
            call.respond(HttpStatusCode.OK, "Success")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid user ID or recipe ID")
        }
    }
}

