package ds.com

import ds.com.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import ds.com.models.UserDTO
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class ApplicationTest {
    @Test
    fun testUserSignUp() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/signUp") {
                val user = UserDTO("testUser", "testPassword")
                val userJson = Json.encodeToString(user)
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(userJson)
            }.apply {
                assertEquals(HttpStatusCode.NoContent, response.status())
            }
        }
    }

    @Test
    fun testUserLogin() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/login?username=testUser&password=testPassword").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Login successful", response.content)
            }
        }
    }
}
