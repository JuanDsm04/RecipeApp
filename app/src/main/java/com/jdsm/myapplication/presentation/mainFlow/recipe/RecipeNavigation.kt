package com.jdsm.myapplication.presentation.mainFlow.recipe

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.jdsm.myapplication.presentation.mainFlow.navigateToMainGraph
import com.jdsm.myapplication.presentation.mainFlow.recipe.form.RecipeFormDestination
import com.jdsm.myapplication.presentation.mainFlow.recipe.form.navigateToRecipeFormScreen
import com.jdsm.myapplication.presentation.mainFlow.recipe.form.recipeFormScreen
import com.jdsm.myapplication.presentation.mainFlow.recipe.list.RecipeListDestination
import com.jdsm.myapplication.presentation.mainFlow.recipe.list.recipeListScreen
import com.jdsm.myapplication.presentation.mainFlow.recipe.profile.navigateToRecipeProfileScreen
import com.jdsm.myapplication.presentation.mainFlow.recipe.profile.recipeProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object  RecipeNavGraph

fun NavGraphBuilder.recipeGraph(
    navController: NavController
) {
    navigation<RecipeNavGraph>(
        startDestination = RecipeListDestination
    ) {
        recipeListScreen(
            onRecipeClick = navController::navigateToRecipeProfileScreen,
            onAddRecipeClick = { navController.navigateToRecipeFormScreen() }
        )
        recipeProfileScreen(
            onNavigationBack = navController::navigateUp
        )
        recipeFormScreen(
            onNavigationBack = navController::navigateUp,
            onAddRecipe = navController::navigateUp
        )
    }
}