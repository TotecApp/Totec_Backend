package ds.com.models
import kotlinx.serialization.Serializable

@Serializable
data class TagRelationDTO (
    val tagId: Int,
    val recipeId: Int
)