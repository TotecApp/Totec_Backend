package ds.com.routes

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import ds.com.models.UserDTO
import ds.com.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class UserRoutesKtTest {

    @Test
    fun signUp() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/signUp") {
                val user = UserDTO("testUser", "testPassword")
                val userJson = Json.encodeToString(user)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(userJson)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun login() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/login?username=testUser&password=testPassword").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Success", response.content)
            }
        }
    }

    @Test
    fun logout() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/logout").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}