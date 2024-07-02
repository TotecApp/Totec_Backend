package ds.com.models

import org.junit.jupiter.api.Test
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*

class PostgresTagRepositoryTest {
    private val repository = mockk<PostgresTagRepository>()

    @Test
    fun allTags() {
        coEvery { repository.allTags() } returns listOf(TagDTO(name = "TestTag"))

        runBlocking {
            val result = repository.allTags()
            assert(result.size == 1)
            assert(result[0].name == "TestTag")
        }
    }

    @Test
    fun tagInfo() {
        coEvery { repository.tagInfo("TestTag") } returns listOf(TagDTO(name = "TestTag"))

        runBlocking {
            val result = repository.tagInfo("TestTag")
            assertEquals("TestTag", result[0].name)
        }
    }

    @Test
    fun getTagId() {
        coEvery { repository.getTagId("TestTag") } returns 1

        runBlocking {
            val result = repository.getTagId("TestTag")
            assertEquals(1, result)
        }
    }

    @Test
    fun tag() {
        coEvery { repository.tag(1) } returns TagDTO(name = "TestTag")

        runBlocking {
            val result = repository.tag(1)
            assertEquals("TestTag", result?.name)
        }
    }

    @Test
    fun addNewTag() {
        coEvery { repository.addNewTag(TagDTO(name = "TestTag")) } returns Unit

        runBlocking {
            val result = repository.addNewTag(TagDTO(name = "TestTag"))
            assertEquals(Unit, result)
        }
    }

    @Test
    fun editTag() {
        coEvery { repository.editTag(1, TagDTO(name = "TestTag")) } returns true

        runBlocking {
            val result = repository.editTag(1, TagDTO(name = "TestTag"))
            assertEquals(true, result)
        }
    }

    @Test
    fun deleteTag() {
        coEvery { repository.deleteTag(1) } returns true

        runBlocking {
            val result = repository.deleteTag(1)
            assertEquals(true, result)
        }
    }
}