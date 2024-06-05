package ds.com

import ds.com.models.UserDTO
import org.junit.Assert.assertEquals
import kotlin.test.Test

class UserDTOTest {
    @Test
    fun criacao() {
        val user = UserDTO("testUser", "testPassword")
        assertEquals("testUser", user.username)
        assertEquals("testPassword", user.password)
    }

    @Test
    fun modificacao() {
        val user = UserDTO("testUser", "testPassword")
        user.password = "newPassword"
        assertEquals("newPassword", user.password)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Usuario vazio`() {
        UserDTO("", "testPassword")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Senha vazia`() {
        UserDTO("testUser", "")
    }
}