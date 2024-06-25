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
import ds.com.models.RecipeRepository
import io.ktor.serialization.*

fun Route.recipeRouting(repository: RecipeRepository){
    route("/allRecipes"){
        recipes(repository)
    }
}

fun Route.recipes(repository: RecipeRepository){
    get{
        try{
            val recipes = repository.allRecipes()
            println(recipes.toString())
            call.application.log.info(recipes.toString())
            call.respondText(Json.encodeToString(recipes), ContentType.Application.Json)
        } catch (ex: ExposedSQLException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
