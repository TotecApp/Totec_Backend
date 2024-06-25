package ds.com.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ds.com.models.UserDTO
import ds.com.models.RecipeDTO
import ds.com.models.FavoritesDTO
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : IntIdTable("Users") {
    val username = varchar("username", 50)
    val password = varchar("password", 50)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)
    var username by UserTable.username
    var password by UserTable.password
}

object RecipeTable : IntIdTable("Recipes") {
    val name = varchar("name", 50)
    val cookingtime = integer("cookingtime")
    val servings = integer("servings")
    val ingredients = text("ingredients")
    val instructions = text("instructions")
}

class RecipeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeDAO>(RecipeTable)
    var name by RecipeTable.name
    var cookingtime by RecipeTable.cookingtime
    var servings by RecipeTable.servings
    var ingredients by RecipeTable.ingredients
    var instructions by RecipeTable.instructions
}

object FavoritesTable : IntIdTable("Favorites") {
    val userId = reference("userId", UserTable)
    val recipeId = reference("recipeId", RecipeTable)
}

class FavoriteDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FavoriteDAO>(FavoritesTable)
    var userId by FavoritesTable.userId
    var recipeId by FavoritesTable.recipeId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModelUser(dao: UserDAO) = UserDTO(
    dao.username,
    dao.password
)

fun daoToModelRecipe(dao: RecipeDAO) = RecipeDTO(
    dao.name,
    dao.cookingtime,
    dao.servings,
    dao.ingredients,
    dao.instructions
)

fun daoToModelFavorite(dao: FavoriteDAO) = FavoritesDTO(
    dao.userId.value,
    dao.recipeId.value
)