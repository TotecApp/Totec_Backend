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

fun Route.recipeRouting(repository: RecipeRepository, repositoryTag: TagRepository,repositoryTagRelation: TagRelationRepository){
    route("/allRecipes"){
        recipes(repository)
    }
    route("/recipeInfo"){
        getRecipeInfo(repository)
    }
    route("/getRecipeId"){
        getRecipeId(repository)
    }
    route("/allResults"){
        getAllResults(repository)
    }
    route("/addRecipe"){
        addRecipe(repository, repositoryTag, repositoryTagRelation)
    }
}

fun Route.recipes(repository: RecipeRepository){
    get{
        try{
            val recipes = repository.allRecipes()
            call.application.log.info(recipes.toString())
            call.respondText(Json.encodeToString(recipes), ContentType.Application.Json)
        } catch (ex: ExposedSQLException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.getRecipeInfo(repository: RecipeRepository){
    get{
        try{
            val recipename = call.parameters["recipe_name"] ?: ""
            val info = repository.recipeInfo(recipename)
            println(info)
            call.respondText(Json.encodeToString(info), ContentType.Application.Json)
        } catch (ex: ExposedSQLException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.getRecipeId(repository: RecipeRepository){
    get{
        val recipeName = call.parameters["recipe_name"] ?: ""
        val id = repository.getRecipeId(recipeName)
        call.respond(HttpStatusCode.OK, id)

        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Route.getAllResults(repository: RecipeRepository){
    get{
        try{
            val searchedString = call.parameters["search_string"] ?: ""
            val recipes = repository.allResults(searchedString)
            call.application.log.info(recipes.toString())
            call.respondText(Json.encodeToString(recipes), ContentType.Application.Json)
        } catch (ex: ExposedSQLException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.addRecipe(repository: RecipeRepository, repositoryTag: TagRepository, repositoryTagRelation: TagRelationRepository){
    post {
        try {
            val recipe = call.receive<RecipeWithTagsDTO>()
            val tags = recipe.tags
            val tagIds = tags.map { repositoryTag.getTagId(it) }
            repository.addNewRecipe(RecipeDTO(recipe.creatorId, recipe.name, recipe.cookingtime, recipe.servings, recipe.ingredients, recipe.instructions, recipe.image))
            val recipeId = repository.getRecipeId(recipe.name)
            for (tagId in tagIds) {
                repositoryTagRelation.addNewTagRelation(TagRelationDTO(tagId, recipeId))
            }
            call.respond(HttpStatusCode.OK, "Success")
        } catch (ex: ExposedSQLException) {
            println("Error: ${ex.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}