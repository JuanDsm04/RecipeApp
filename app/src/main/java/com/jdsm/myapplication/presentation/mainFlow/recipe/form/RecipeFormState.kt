package com.jdsm.myapplication.presentation.mainFlow.recipe.form

data class RecipeFormState(
    val title: String = "",
    val description: String = "",
    val preparationTime: String = "",
    val isFavorite: Boolean = false,
    val hasEmpty: Boolean = false,
    val hasError: Boolean = false,
    val hasErrorTime: Boolean = false,
    val successfulSave: Boolean = false
)
