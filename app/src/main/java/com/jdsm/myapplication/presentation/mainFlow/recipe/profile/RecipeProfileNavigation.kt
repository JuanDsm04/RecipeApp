package com.jdsm.myapplication.presentation.mainFlow.recipe.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class RecipeProfileDestination(
    val recipeId: Int
)

fun NavController.navigateToRecipeProfileScreen(
    recipeId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = RecipeProfileDestination(recipeId = recipeId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.recipeProfileScreen(
    onNavigationBack: () -> Unit
) {
    composable<RecipeProfileDestination> {
        RecipeProfileRoute (
            onNavigationBack = onNavigationBack
        )
    }
}