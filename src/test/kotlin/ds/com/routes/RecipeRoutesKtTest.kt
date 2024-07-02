package ds.com.routes

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import ds.com.models.RecipeDTO
import ds.com.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class RecipeRoutesKtTest {

    @Test
    fun recipes() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/allRecipes") {
                val recipe = RecipeDTO("testRecipe", 20, 4, "testInstructions", "testIngredients", "testTags")
                val recipeJson = Json.encodeToString(recipe)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(recipeJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun getRecipeInfo() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/recipeInfo?recipe_name=testRecipe") {
                val recipe = RecipeDTO("testRecipe", 20, 4, "testInstructions", "testIngredients", "testTags")
                val recipeJson = Json.encodeToString(recipe)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(recipeJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun getRecipeId() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/getRecipeId?recipe_name=testRecipe").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun getAllResults() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/allResults") {
                val recipe = RecipeDTO("testRecipe", 20, 4, "testInstructions", "testIngredients", "testTags")
                val recipeJson = Json.encodeToString(recipe)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(recipeJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}