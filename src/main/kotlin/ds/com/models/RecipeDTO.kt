package ds.com.models
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDTO(
    val name: String,
    val cookingtime: Int,
    val servings: Int,
    val ingredients: String,
    var instructions: String,
    val image: String
) {
    init {
        require(name.isNotEmpty()) { "Nome não pode ser vazio" }
        require(cookingtime >= 0) { "Tempo de cozimento não pode ser negativo" }
        require(servings > 0) { "Porções não podem ser negativas" }
        require(ingredients.isNotEmpty()) { "Ingredientes não podem ser vazios" }
        require(instructions.isNotEmpty()) { "Instruções não podem ser vazias" }
        require(image.isNotEmpty()) { "Imagem não pode ser vazia" }
    }
}