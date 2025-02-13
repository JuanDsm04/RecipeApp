package com.jdsm.myapplication.presentation.mainFlow.recipe.list

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jdsm.myapplication.presentation.mainFlow.recipe.form.RecipeFormDestination
import kotlinx.serialization.Serializable

@Serializable
data object RecipeListDestination

fun NavGraphBuilder.recipeListScreen(
    onRecipeClick: (Int) -> Unit,
    onAddRecipeClick: () -> Unit
) {
    composable<RecipeListDestination> {
        RecipeListRoute(
            onRecipeClick = onRecipeClick,
            onAddRecipeClick = onAddRecipeClick
        )
    }
}