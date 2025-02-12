package com.jdsm.myapplication.presentation.mainFlow.recipe.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jdsm.myapplication.R
import com.jdsm.myapplication.data.model.Recipe
import com.jdsm.myapplication.data.source.RecipeDb
import com.jdsm.myapplication.ui.theme.RecipeAppTheme

@Composable
fun RecipeListRoute(
    onRecipeClick: (Int) -> Unit,
    viewModel: RecipeListViewModel = viewModel(factory = RecipeListViewModel.Factory)
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshRecipes()
    }

    RecipeListScreen(
        state = state,
        onRecipeClick = onRecipeClick,
        onToggleFavoritesFilter = { viewModel.toggleFavoriteFilter()},
        onToggleSortByTime = { viewModel.toggleSortByTime() },
        onAddRecipeClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeListScreen(
    state: RecipeListState,
    onRecipeClick: (Int) -> Unit,
    onToggleFavoritesFilter: () -> Unit,
    onToggleSortByTime: () -> Unit,
    onAddRecipeClick: () -> Unit
) {
    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.recipes))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddRecipeClick() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        CustomFilterChip(
                            isSelected = state.filterByFavorites,
                            label = stringResource(id = R.string.favorites),
                            onClick = onToggleFavoritesFilter
                        )
                    }
                    item {
                        CustomFilterChip(
                            isSelected = state.sortByTime,
                            label = stringResource(id = R.string.time),
                            onClick = onToggleSortByTime
                        )
                    }
                }

            }

            if (state.recipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_recipes_found),
                        fontSize = 20.sp
                    )
                }

            } else {
                LazyColumn(modifier = Modifier.padding(horizontal = 20.dp)) {
                    items(state.recipes) { item ->
                        RecipeItem(
                            recipe = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onRecipeClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomFilterChip(
    isSelected:Boolean,
    label: String,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSelected){
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.check_icon),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(label)
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.tertiary,
            selectedLabelColor = MaterialTheme.colorScheme.onTertiary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onTertiary
        )
    )
}

@Composable
private fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.tacos),
                contentDescription = stringResource(id = R.string.recipe_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(3f)
                ) {
                    Text(
                        text = recipe.title,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.preparation_time, recipe.preparationTime),
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Icon(
                    imageVector = if (recipe.favorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.favorite_icon),
                    modifier = Modifier.size(24.dp),
                    tint = if (recipe.favorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun PreviewRecipeListScreen() {
    RecipeAppTheme {
        Surface {
            val recipesDb = RecipeDb()
            RecipeListScreen(
                state = RecipeListState(recipes = recipesDb.getAllRecipes()),
                onRecipeClick = {},
                onToggleFavoritesFilter = {},
                onToggleSortByTime = {},
                onAddRecipeClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewEmptyRecipeList() {
    RecipeAppTheme {
        Surface {
            RecipeListScreen(
                state = RecipeListState(recipes = emptyList()),
                onRecipeClick = {},
                onToggleFavoritesFilter = {},
                onToggleSortByTime = {},
                onAddRecipeClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFavoritesFilter() {
    RecipeAppTheme {
        Surface {
            RecipeListScreen(
                state = RecipeListState(
                    recipes = listOf(
                        Recipe(id = 1, title = "Tacos", description = "Delicious tacos", preparationTime = 20, favorite = true),
                        Recipe(id = 2, title = "Burger", description = "Juicy burger", preparationTime = 15, favorite = true)
                    ),
                    filterByFavorites = true,
                    sortByTime = false
                ),
                onRecipeClick = {},
                onToggleFavoritesFilter = {},
                onToggleSortByTime = {},
                onAddRecipeClick = {}
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTimeFilter() {
    RecipeAppTheme {
        Surface {
            RecipeListScreen(
                state = RecipeListState(
                    recipes = listOf(
                        Recipe(id = 2, title = "Burger", description = "Juicy burger", preparationTime = 15, favorite = false),
                        Recipe(id = 1, title = "Tacos", description = "Delicious tacos", preparationTime = 20, favorite = false)
                    ),
                    filterByFavorites = false,
                    sortByTime = true
                ),
                onRecipeClick = {},
                onToggleFavoritesFilter = {},
                onToggleSortByTime = {},
                onAddRecipeClick = {}
            )
        }
    }
}