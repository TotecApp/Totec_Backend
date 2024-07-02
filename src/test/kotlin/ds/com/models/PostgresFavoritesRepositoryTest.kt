package ds.com.models

import org.junit.jupiter.api.Test
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*

class PostgresFavoritesRepositoryTest {
    private val repository = mockk<PostgresFavoritesRepository>()

    @Test
    fun allFavorites() {
        coEvery { repository.allFavorites(1) } returns listOf(RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage"))

        runBlocking {
            val result = repository.allFavorites(1)
            assert(result.size == 1)
            assert(result[0].name == "TestRecipe")
            assert(result[0].cookingtime == 10)
            assert(result[0].servings == 2)
            assert(result[0].ingredients == "TestIngredients")
            assert(result[0].instructions == "TestInstructions")
            assert(result[0].image == "TestImage")
        }
    }

    @Test
    fun isFavorite() {
        coEvery { repository.isFavorite(1, 1) } returns true

        runBlocking {
            val result = repository.isFavorite(1, 1)
            assertEquals(true, result)
        }
    }

    @Test
    fun addNewFavorite() {
        val favorite = FavoritesDTO(1, 1)
        coEvery { repository.addNewFavorite(favorite) } returns Unit

        runBlocking {
            repository.addNewFavorite(favorite)
        }
    }

    @Test
    fun deleteFavorite() {
        coEvery { repository.deleteFavorite(1, 1) } returns true

        runBlocking {
            val result = repository.deleteFavorite(1, 1)
            assertEquals(true, result)
        }
    }
}