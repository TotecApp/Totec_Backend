package ds.com.models

import org.junit.jupiter.api.Test
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*

class PostgresTagRelationRepositoryTest {
    private val repository = mockk<PostgresTagRelationRepository>()

    @Test
    fun allTagRelations() {
        coEvery { repository.allTagRelations() } returns listOf(TagRelationDTO(tagId = 1, recipeId = 1))

        runBlocking {
            val result = repository.allTagRelations()
            assert(result.size == 1)
            assert(result[0].tagId == 1)
            assert(result[0].recipeId == 1)
        }
    }

    @Test
    fun getTaggedRecipes() {
        coEvery { repository.getTaggedRecipes(listOf(1)) } returns listOf(TagRelationDTO(tagId = 1, recipeId = 1))

        runBlocking {
            val result = repository.getTaggedRecipes(listOf(1))
            assert(result.size == 1)
            assert(result[0].tagId == 1)
            assert(result[0].recipeId == 1)
        }
    }

    @Test
    fun getRecipeTags() {
        coEvery { repository.getRecipeTags(1) } returns listOf(TagRelationDTO(tagId = 1, recipeId = 1))

        runBlocking {
            val result = repository.getRecipeTags(1)
            assert(result.size == 1)
            assert(result[0].tagId == 1)
            assert(result[0].recipeId == 1)
        }
    }

    @Test
    fun addNewTagRelation() {
        coEvery { repository.addNewTagRelation(TagRelationDTO(tagId = 1, recipeId = 1)) } returns Unit

        runBlocking {
            val result = repository.addNewTagRelation(TagRelationDTO(tagId = 1, recipeId = 1))
            assertEquals(Unit, result)
        }
    }

    @Test
    fun deleteTagRelation() {
        coEvery { repository.deleteTagRelation(1, 1) } returns true

        runBlocking {
            val result = repository.deleteTagRelation(1, 1)
            assertEquals(true, result)
        }
    }
}