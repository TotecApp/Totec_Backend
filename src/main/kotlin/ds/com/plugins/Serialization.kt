package ds.com.plugins

import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import ds.com.models.*
import ds.com.routes.recipeRouting
import ds.com.routes.userRouting


fun Application.configureSerialization(repositoryUser: UserRepository, repositoryRecipe: RecipeRepository) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    routing {
        userRouting(repositoryUser)
        recipeRouting(repositoryRecipe)
    }
}
