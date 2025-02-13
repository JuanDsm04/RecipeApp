package com.jdsm.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.jdsm.myapplication.R
import com.jdsm.myapplication.presentation.mainFlow.profile.ProfileDestination
import com.jdsm.myapplication.presentation.mainFlow.recipe.RecipeNavGraph
import com.jdsm.myapplication.presentation.mainFlow.recipe.list.RecipeListDestination

data class NavItem(
    val title: String,
    val selectedIcon: Painter,
    val destination: Any,
)

@Composable
fun getNavigationItems(): List<NavItem> {
    return listOf(
        NavItem(
            title = stringResource(id = R.string.recipes),
            selectedIcon = painterResource(id = R.drawable.recipe),
            destination = RecipeNavGraph
        ),
        NavItem(
            title = stringResource(id = R.string.profile),
            selectedIcon = painterResource(id = R.drawable.profile),
            destination = ProfileDestination
        )
    )
}

val topLevelDestinations = listOf(
    RecipeListDestination::class,
    ProfileDestination::class
)
