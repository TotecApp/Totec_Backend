package ds.com.models

interface RecipeRepository {
    suspend fun allRecipes(): List<RecipeDTO>
    suspend fun recipeInfo(recipename: String): List<RecipeDTO>
    suspend fun getRecipeId(name: String): Int
    suspend fun recipe(id: Int): RecipeDTO?
    suspend fun allResults(searchedString: String): List<RecipeDTO>
    suspend fun addNewRecipe(recipe: RecipeDTO)
    suspend fun editRecipe(id: Int, newRecipe: RecipeDTO): Boolean
    suspend fun deleteRecipe(id: Int): Boolean
    suspend fun addedRecipes(creatorId: Int): List<RecipeDTO>
}