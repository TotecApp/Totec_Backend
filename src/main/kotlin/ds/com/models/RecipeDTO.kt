package ds.com.models
import ds.com.models.Tags
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val name: String,
    val cookingtime: Int,
    //val utensils: List<String>,
    val servings: Int,
    val ingredients: String,
    val instructions: String,
    val image: String
    //val tags: Tags
)