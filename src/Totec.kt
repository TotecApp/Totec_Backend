class User(
    val username: String,
    var password: String,
    var favorites: MutableList<Int> = mutableListOf(),
    var restrictions: MutableList<String>
) {
    fun addFavorite(recipeId: Int){
        // Add recipe to favorites
        this.favorites.add(recipeId)
    }
    fun removeFavorite(recipeId: Int){
        // Remove recipe from favorites
        this.favorites.remove(recipeId)
    }
    fun addRestriction(restriction: String){
        // Add restriction to user
        this.restrictions.add(restriction)
    }
    fun removeRestriction(restriction: String){
        // Remove restriction from user
        this.restrictions.remove(restriction)
    }
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
    fun displayDetails(){
        // Display recipe details
        println("Name: $name")
        println("Ingredients: $ingredients")
        println("Cooking Time: $cookingTime")
        println("Utensils: $utensils")
        println("Servings: $servings")
        println("Instructions: $instructions")
        println("Tags: $tags")
    }
}

class Tags(
    val diets: List<String>,
    val courses: List<String>,
    val holidays: List<String>,
    val regions: List<String>,
    val countries: List<String>
)

