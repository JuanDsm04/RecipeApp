package com.jdsm.myapplication.presentation.mainFlow.recipe.form

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object RecipeFormDestination

fun NavController.navigateToRecipeFormScreen(
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = RecipeFormDestination,
        navOptions = navOptions
    )
}

fun NavGraphBuilder.recipeFormScreen(
    onNavigationBack: () -> Unit,
    onAddRecipe: () -> Unit
){
    composable<RecipeFormDestination> {
        RecipeFormRoute(
            onNavigationBack = onNavigationBack,
            onAddRecipe = onAddRecipe
        )
    }
}