package com.jdsm.myapplication.presentation.mainFlow.recipe.form

sealed interface RecipeFormEvent {
    data class TitleChange(val title: String) : RecipeFormEvent
    data class DescriptionChange(val description: String) : RecipeFormEvent
    data class PreparationTimeChange(val preparationTime: String) : RecipeFormEvent
    data class FavoriteChange(val isFavorite: Boolean) : RecipeFormEvent
    data object AddRecipe : RecipeFormEvent
}