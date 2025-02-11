package com.jdsm.myapplication.presentation.mainFlow.recipe.list

import com.jdsm.myapplication.data.model.Recipe

data class RecipeListState (
    val recipes: List<Recipe> = emptyList(),
    val filterByFavorites: Boolean = false,
    val sortByTime: Boolean = false
)