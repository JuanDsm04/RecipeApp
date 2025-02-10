package com.jdsm.myapplication.data.source

import com.jdsm.myapplication.data.model.Recipe

class RecipeDb {
    private val recipes: List<Recipe> = listOf(
        Recipe(1, "Spaghetti Bolognese", "Delicious Italian pasta with meat sauce.", 30, false),
        Recipe(2, "Caesar Salad", "Fresh salad with croutons and dressing.", 15, true),
        Recipe(3, "Chicken Curry", "Spicy and creamy Indian-style chicken curry.", 45, false),
        Recipe(4, "Chocolate Cake", "Moist and rich chocolate cake.", 60, true),
        Recipe(5, "Omelette", "Fluffy omelette with cheese and ham.", 10, false)
    )

    fun getAllRecipes(): List<Recipe> {
        return recipes
    }

    fun getRecipeById(id: Int): Recipe {
        return recipes.first { it.id == id }
    }
}