package ds.com.models
import ds.com.db.*
import ds.com.db.daoToModelRecipe as daoToModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase

class PostgresRecipeRepository : RecipeRepository {
    override suspend fun allRecipes(): List<RecipeDTO> = suspendTransaction {
        RecipeDAO.all().map(::daoToModel)
    }

    override suspend fun recipeInfo(recipename: String): List<RecipeDTO> = suspendTransaction {
        RecipeDAO
            .find{ RecipeTable.name eq recipename}
            .map(::daoToModel)
    }

    override suspend fun getRecipeId(name: String): Int = suspendTransaction {
        val recipe = RecipeDAO.find{ RecipeTable.name eq name}.firstOrNull()
        recipe?.id?.value ?: -1
    }

    override suspend fun recipe(id: Int): RecipeDTO? = suspendTransaction {
        RecipeDAO
            .find { RecipeTable.id eq id }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun allResults(searchedString: String): List<RecipeDTO> = suspendTransaction{
        RecipeDAO
            .find { RecipeTable.name.lowerCase() like "%${searchedString.toLowerCase()}%" }
            .map {daoToModel(it)}
    }


    override suspend fun addNewRecipe(recipe: RecipeDTO): Unit = suspendTransaction {
        RecipeDAO.new {
            name = recipe.name
            cookingtime = recipe.cookingtime
            servings = recipe.servings
            ingredients = recipe.ingredients
            instructions = recipe.instructions
            image = recipe.image
        }
    }

    override suspend fun editRecipe(id: Int, newRecipe: RecipeDTO): Boolean = suspendTransaction {
        RecipeDAO
            .find { RecipeTable.id eq id }
            .limit(1)
            .map {
                it.name = newRecipe.name
                it.cookingtime = newRecipe.cookingtime
                it.servings = newRecipe.servings
                it.ingredients = newRecipe.ingredients
                it.instructions = newRecipe.instructions
                it.image = newRecipe.image
            }
            .firstOrNull() != null
    }

    override suspend fun deleteRecipe(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = RecipeTable.deleteWhere { RecipeTable.id eq id }
        rowsDeleted == 1
    }
}