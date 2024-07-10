package ds.com.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import ds.com.models.UserDTO
import ds.com.models.RecipeDTO
import ds.com.models.FavoritesDTO
import ds.com.models.TagDTO
import ds.com.models.TagRelationDTO
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
    val creatorId = integer("creatorId")
    val name = varchar("name", 50)
    val cookingtime = integer("cookingtime")
    val servings = integer("servings")
    val ingredients = text("ingredients")
    val instructions = text("instructions")
    val image = text("image")
}

class RecipeDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeDAO>(RecipeTable)
    var creatorId by RecipeTable.creatorId
    var name by RecipeTable.name
    var cookingtime by RecipeTable.cookingtime
    var servings by RecipeTable.servings
    var ingredients by RecipeTable.ingredients
    var instructions by RecipeTable.instructions
    var image by RecipeTable.image
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

object TagTable : IntIdTable("Tags") {
    val name = varchar("name", 50)
}

class TagDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TagDAO>(TagTable)
    var name by TagTable.name
}

object TagRelationTable : IntIdTable("TagRelations") {
    val recipeId = reference("recipeId", RecipeTable)
    val tagId = reference("tagId", TagTable)
}

class TagRelationDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TagRelationDAO>(TagRelationTable)
    var recipeId by TagRelationTable.recipeId
    var tagId by TagRelationTable.tagId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModelUser(dao: UserDAO) = UserDTO(
    dao.username,
    dao.password
)

fun daoToModelRecipe(dao: RecipeDAO) = RecipeDTO(
    dao.creatorId,
    dao.name,
    dao.cookingtime,
    dao.servings,
    dao.ingredients,
    dao.instructions,
    dao.image
)

fun daoToModelFavorite(dao: FavoriteDAO) = FavoritesDTO(
    dao.userId.value,
    dao.recipeId.value
)

fun daoToModelTag(dao: TagDAO) = TagDTO(
    dao.name
)

fun daoToModelTagRelation(dao: TagRelationDAO) = TagRelationDTO(
    dao.tagId.value,
    dao.recipeId.value

)