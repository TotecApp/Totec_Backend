package br.com.ds.models

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