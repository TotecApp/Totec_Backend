package ds.com.models

import org.junit.jupiter.api.Assertions.*

class RecipeDTOTest {
    @org.junit.jupiter.api.Test
    fun criacao() {
        val recipe = RecipeDTO("testRecipe", 10, 2, "testIngredients", "testInstructions", "testImage")
        assertEquals("testRecipe", recipe.name)
        assertEquals(10, recipe.cookingtime)
        assertEquals(2, recipe.servings)
        assertEquals("testIngredients", recipe.ingredients)
        assertEquals("testInstructions", recipe.instructions)
        assertEquals("testImage", recipe.image)
    }

    @org.junit.jupiter.api.Test
    fun modificacao() {
        val recipe = RecipeDTO("testRecipe", 10, 2, "testIngredients", "testInstructions", "testImage")
        recipe.instructions = "newInstructions"
        assertEquals("newInstructions", recipe.instructions)
    }

    @org.junit.jupiter.api.Test
    fun `Nome vazio`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("", 10, 2, "testIngredients", "testInstructions", "testImage")
        }
    }

    @org.junit.jupiter.api.Test
    fun `Tempo de cozimento negativo`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("testRecipe", -10, 2, "testIngredients", "testInstructions", "testImage")
        }
    }

    @org.junit.jupiter.api.Test
    fun `Porcoes negativas`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("testRecipe", 10, -2, "testIngredients", "testInstructions", "testImage")
        }
    }

    @org.junit.jupiter.api.Test
    fun `Ingredientes vazios`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("testRecipe", 10, 2, "", "testInstructions", "testImage")
        }
    }

    @org.junit.jupiter.api.Test
    fun `Instrucoes vazias`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("testRecipe", 10, 2, "testIngredients", "", "testImage")
        }
    }

    @org.junit.jupiter.api.Test
    fun `Imagem vazia`() {
        assertThrows(IllegalArgumentException::class.java) {
            RecipeDTO("testRecipe", 10, 2, "testIngredients", "testInstructions", "")
        }
    }
}