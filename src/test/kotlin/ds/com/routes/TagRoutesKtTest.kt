package ds.com.routes

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import ds.com.models.TagDTO
import ds.com.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class TagRoutesKtTest {

    @Test
    fun allTags() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/allTags") {
                val tag = TagDTO("testTag")
                val tagJson = Json.encodeToString(tag)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(tagJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun taggedRecipes() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/taggedRecipes") {
                val tagNames = listOf("testTag")
                val tagNamesJson = Json.encodeToString(tagNames)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(tagNamesJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}