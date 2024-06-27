package ds.com.models

interface FavoritesRepository {
    suspend fun allFavorites(): List<FavoritesDTO>
    suspend fun isFavorite(userId: Int, recipeId: Int): Boolean
    suspend fun addNewFavorite(favorite: FavoritesDTO)
    suspend fun deleteFavorite(userId: Int, recipeId: Int): Boolean
}