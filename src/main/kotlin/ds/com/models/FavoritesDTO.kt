package ds.com.models
import kotlinx.serialization.Serializable

@Serializable
class FavoritesDTO (
    val userId: Int,
    val recipeId: Int
)