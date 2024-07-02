package ds.com.models

import org.junit.jupiter.api.Test
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*

class PostgresRecipeRepositoryTest {
    private val repository = mockk<PostgresRecipeRepository>()

    @Test
    fun allRecipes() = runBlocking {
        coEvery { repository.allRecipes() } returns listOf(RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage"))

        val result = repository.allRecipes()
        assert(result.size == 1)
        assert(result[0].name == "TestRecipe")
        assert(result[0].cookingtime == 10)
        assert(result[0].servings == 2)
        assert(result[0].ingredients == "TestIngredients")
        assert(result[0].instructions == "TestInstructions")
        assert(result[0].image == "TestImage")
    }

    @Test
    fun recipeInfo() {
        coEvery { repository.recipeInfo("TestRecipe") } returns listOf(RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage"))

        runBlocking {
            val result = repository.recipeInfo("TestRecipe")
            assertEquals("TestRecipe", result[0].name)
            assertEquals(10, result[0].cookingtime)
            assertEquals(2, result[0].servings)
            assertEquals("TestIngredients", result[0].ingredients)
            assertEquals("TestInstructions", result[0].instructions)
            assertEquals("TestImage", result[0].image)
        }
    }

    @Test
    fun getRecipeId() {
        coEvery { repository.getRecipeId("TestRecipe") } returns 1

        runBlocking {
            val result = repository.getRecipeId("TestRecipe")
            assertEquals(1, result)
        }
    }

    @Test
    fun recipe() {
        coEvery { repository.recipe(1) } returns RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage")

        runBlocking {
            val result = repository.recipe(1)
            assertEquals("TestRecipe", result?.name)
            assertEquals(10, result?.cookingtime)
            assertEquals(2, result?.servings)
            assertEquals("TestIngredients", result?.ingredients)
            assertEquals("TestInstructions", result?.instructions)
            assertEquals("TestImage", result?.image)
        }
    }

    @Test
    fun allResults() {
        coEvery { repository.allResults("TestRecipe") } returns listOf(RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage"))

        runBlocking {
            val result = repository.allResults("TestRecipe")
            assertEquals("TestRecipe", result[0].name)
            assertEquals(10, result[0].cookingtime)
            assertEquals(2, result[0].servings)
            assertEquals("TestIngredients", result[0].ingredients)
            assertEquals("TestInstructions", result[0].instructions)
            assertEquals("TestImage", result[0].image)
        }
    }

    @Test
    fun addNewRecipe() {
        val recipe = RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage")
        coEvery { repository.addNewRecipe(recipe) } returns Unit

        runBlocking {
            repository.addNewRecipe(recipe)
        }
    }

    @Test
    fun editRecipe() {
        coEvery { repository.editRecipe(1, RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage")) } returns true

        runBlocking {
            val result = repository.editRecipe(1, RecipeDTO(name = "TestRecipe", cookingtime = 10, servings = 2, ingredients = "TestIngredients", instructions = "TestInstructions", image = "TestImage"))
            assertEquals(true, result)
        }
    }

    @Test
    fun deleteRecipe() {
        coEvery { repository.deleteRecipe(1) } returns true

        runBlocking {
            val result = repository.deleteRecipe(1)
            assertEquals(true, result)
        }
    }
}