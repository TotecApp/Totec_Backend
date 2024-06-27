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
    route("/modifyFavorite"){
        modifyFavorite(repository)
    }
    route("/isFavorite"){
        isFavorite(repository)
    }
    route("/allFavorites"){
        allFavorites(repository)
    }
}
@Serializable
data class AddFavoriteRequest(val userId: Int, val recipeId: Int)

fun Route.modifyFavorite(repository: FavoritesRepository){
    post{
        val favoriteRequest = call.receive<AddFavoriteRequest>()
        val userId = favoriteRequest.userId
        val recipeId = favoriteRequest.recipeId
        val isFav = repository.isFavorite(userId, recipeId)
        if (!isFav) {
            val favorite = FavoritesDTO(userId, recipeId)
            repository.addNewFavorite(favorite)
            call.respond(HttpStatusCode.OK, "Success")
        } else {
            repository.deleteFavorite(userId, recipeId)
            call.respond(HttpStatusCode.OK, "Success")
        }
    }
}

fun Route.isFavorite(repository: FavoritesRepository) {
    get{
        val userId = call.request.queryParameters["userId"]?.toIntOrNull()
        val recipeId = call.request.queryParameters["recipeId"]?.toIntOrNull()
        println("userId: $userId | recipeId: $recipeId")
        if (userId != null && recipeId != null) {
            val isFavorite = repository.isFavorite(userId, recipeId)

            call.respond(HttpStatusCode.OK, mapOf("isFavorite" to isFavorite))
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid user ID or recipe ID")
        }
    }
}

fun Route.allFavorites(repository: FavoritesRepository) {
    get{
        val userId = call.request.queryParameters["userId"]?.toIntOrNull()
        if (userId != null) {
            val recipes = repository.allFavorites(userId)
            call.respond(HttpStatusCode.OK, recipes)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid or missing user ID")
        }
    }
}