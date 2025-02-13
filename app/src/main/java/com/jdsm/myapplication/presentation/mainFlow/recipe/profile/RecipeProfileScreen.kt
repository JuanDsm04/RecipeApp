package com.jdsm.myapplication.presentation.mainFlow.recipe.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jdsm.myapplication.R
import com.jdsm.myapplication.data.source.RecipeDb
import com.jdsm.myapplication.ui.theme.RecipeAppTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jdsm.myapplication.presentation.common.CustomTextButton
import com.jdsm.myapplication.presentation.common.CustomTopAppBar

@Composable
fun RecipeProfileRoute(
    viewModel: RecipeProfileViewModel = viewModel(factory = RecipeProfileViewModel.Factory),
    onNavigationBack: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    RecipeProfileScreen(
        state = state,
        onNavigationBack = onNavigationBack,
        onFavorites = { viewModel.toggleFavorite() }
    )
}

@Composable
private fun RecipeProfileScreen(
    state: RecipeProfileState,
    onNavigationBack: () -> Unit,
    onFavorites: () -> Unit
){
    val recipe = state.data
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        CustomTopAppBar(
            title = stringResource(id = R.string.recipe_detail),
            onNavigationBack = { onNavigationBack() }
        )
        Image(
            painter = painterResource(id = R.drawable.food),
            contentDescription = stringResource(id = R.string.recipe_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface)
                .padding(30.dp)
        ) {
            if (recipe != null) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.preparation_time, recipe.preparationTime),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        CustomTextButton(
            text = if (recipe != null && recipe.favorite)
                stringResource(id = R.string.remove_favorites)
            else
                stringResource(id = R.string.add_favorites),
            onClick = { onFavorites() },
            containerColor = MaterialTheme.colorScheme.tertiary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            padding = 16.dp,
            height = 50.dp
        )
    }
}

@Preview
@Composable
private fun PreviewProfileScreenAddFavorites() {
    RecipeAppTheme {
        Surface {
            val recipeDb = RecipeDb()
            RecipeProfileScreen(
                state = RecipeProfileState(data = recipeDb.getRecipeById(1)),
                onNavigationBack = {},
                onFavorites = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProfileScreenRemoveFavorites() {
    RecipeAppTheme {
        Surface {
            val recipeDb = RecipeDb()
            RecipeProfileScreen(
                state = RecipeProfileState(data = recipeDb.getRecipeById(2)),
                onNavigationBack = {},
                onFavorites = {}
            )
        }
    }
}