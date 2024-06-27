package ds.com.models
import ds.com.db.FavoriteDAO
import ds.com.db.daoToModelFavorite as daoToModel
import ds.com.db.FavoritesTable
import ds.com.db.UserTable
import ds.com.db.RecipeTable
import ds.com.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.dao.id.EntityID

class PostgresFavoritesRepository : FavoritesRepository {
    override suspend fun allFavorites(): List<FavoritesDTO> = suspendTransaction {
        FavoriteDAO.all().map(::daoToModel)
    }

    override suspend fun isFavorite(userId: Int, recipeId: Int): Boolean = suspendTransaction {
        FavoriteDAO
            .find { (FavoritesTable.userId eq userId) and (FavoritesTable.recipeId eq recipeId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
            ?.userId != null
    }


    override suspend fun addNewFavorite(favorite: FavoritesDTO): Unit = suspendTransaction {
        FavoriteDAO.new {
            userId = EntityID(favorite.userId, UserTable)
            recipeId = EntityID(favorite.recipeId, RecipeTable)
        }
    }

    override suspend fun deleteFavorite(userId: Int, recipeId: Int): Boolean = suspendTransaction {
        val rowsDeleted = FavoritesTable.deleteWhere { (FavoritesTable.userId eq userId) and (FavoritesTable.recipeId eq recipeId) }
        rowsDeleted == 1
    }
}