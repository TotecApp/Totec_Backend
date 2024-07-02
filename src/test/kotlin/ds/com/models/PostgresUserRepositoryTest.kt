package ds.com.models

import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class PostgresUserRepositoryTest {
    private val repository = mockk<PostgresUserRepository>()

    @Test
    fun allUsers() = runBlocking {
        coEvery { repository.allUsers() } returns listOf(UserDTO(username = "TestUser", password = "TestPassword"))

        val result = repository.allUsers()
        assert(result.size == 1)
        assert(result[0].username == "TestUser")
        assert(result[0].password == "TestPassword")
    }

    @Test
    fun user() {
        coEvery { repository.user("TestUser") } returns UserDTO("TestUser", "TestPassword")

        runBlocking {
            val result = repository.user("TestUser")
            assertEquals("TestUser", result?.username)
            assertEquals("TestPassword", result?.password)
        }
    }

    @Test
    fun addNewUser() {
        val user = UserDTO("TestUser", "TestPassword")
        coEvery { repository.addNewUser(user) } returns Unit

        runBlocking {
            repository.addNewUser(user)
        }
    }

    @Test
    fun getUserId() {
        coEvery { repository.getUserId("TestUser") } returns 1

        runBlocking {
            val result = repository.getUserId("TestUser")
            assertEquals(1, result)
        }
    }

    @Test
    fun editUser() {
        coEvery { repository.editUser("TestUser", "NewUser", "NewPassword") } returns true

        runBlocking {
            val result = repository.editUser("TestUser", "NewUser", "NewPassword")
            assertEquals(true, result)
        }
    }

    @Test
    fun deleteUser() {
        coEvery { repository.deleteUser("TestUser") } returns true

        runBlocking {
            val result = repository.deleteUser("TestUser")
            assertEquals(true, result)
        }
    }
}