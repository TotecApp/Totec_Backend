package ds.com.routes

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import ds.com.models.FavoritesDTO
import ds.com.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class FavoritesRoutesKtTest {

    @Test
    fun modifyFavorite() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/modifyFavorite") {
                val favorite = FavoritesDTO(1, 1)
                val favoriteJson = Json.encodeToString(favorite)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(favoriteJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun isFavorite() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/isFavorite?userId=1&recipeId=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun allFavorites() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/taggedRecipes") {
                val tagNames = listOf("testTag1", "testTag2")
                val tagNamesJson = Json.encodeToString(tagNames)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(tagNamesJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}