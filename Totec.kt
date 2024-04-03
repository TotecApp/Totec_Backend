class User(
    val username: List<String>,
    val password: List<String>,
    var favorites: List<Recipe>,
    var restrictions: List<String>
) {
    fun updateFavorite(recipeId: Int)
    fun updateRestrictions(restrictions: List<String>)
}

class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val cookingTime: Int,
    val utensils: List<String>,
    val servings: Int,
    val instructions: List<String>,
    val tags: Tags
) {
    fun displayDetails()
}

class Tags(
    val diets: List<String>,
    val courses: List<String>,
    val holidays: List<String>,
    val regions: List<String>,
    val countries: List<String>
)