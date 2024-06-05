package com.ds.models

import ds.com.models.Tags

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
        println("Name: $name")
        println("Ingredients: $ingredients")
        println("Cooking Time: $cookingTime")
        println("Utensils: $utensils")
        println("Servings: $servings")
        println("Instructions: $instructions")
        println("Tags: $tags")
    }
}