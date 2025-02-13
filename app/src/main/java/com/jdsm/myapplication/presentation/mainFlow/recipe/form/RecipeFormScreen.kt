package com.jdsm.myapplication.presentation.mainFlow.recipe.form

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jdsm.myapplication.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jdsm.myapplication.presentation.common.CustomOutlinedTextField
import com.jdsm.myapplication.presentation.common.CustomTextButton
import com.jdsm.myapplication.presentation.common.CustomTopAppBar
import com.jdsm.myapplication.ui.theme.RecipeAppTheme


@Composable
fun RecipeFormRoute(
    onNavigationBack: () -> Unit,
    onAddRecipe: () -> Unit,
    viewModel: RecipeFormViewModel = viewModel(factory = RecipeFormViewModel.Factory(LocalContext.current))
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.successfulSave) {
        if (state.successfulSave) {
            Toast.makeText(context, context.getString(R.string.recipe_added), Toast.LENGTH_SHORT).show()
            onAddRecipe()
        }
    }

    RecipeFormScreen(
        state = state,
        onTitleChange = {
            viewModel.onEvent(RecipeFormEvent.TitleChange(it))
        },
        onDescriptionChange = {
            viewModel.onEvent(RecipeFormEvent.DescriptionChange(it))
        },
        onPreparationTimeChange = {
            viewModel.onEvent(RecipeFormEvent.PreparationTimeChange(it))
        },
        onFavoriteChange = {
            viewModel.onEvent(RecipeFormEvent.FavoriteChange(it))
        },
        onAddRecipe = {
            viewModel.onEvent(RecipeFormEvent.AddRecipe)
        },
        onNavigationBack = onNavigationBack
    )
}

@Composable
private fun RecipeFormScreen(
    state: RecipeFormState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPreparationTimeChange: (String) -> Unit,
    onFavoriteChange: (Boolean) -> Unit,
    onAddRecipe: () -> Unit,
    onNavigationBack: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTopAppBar(
            title = stringResource(id = R.string.new_recipe),
            onNavigationBack = { onNavigationBack() }
        )

        Box (
            modifier = Modifier.padding(16.dp)
        ){
            Column {
                CustomOutlinedTextField(
                    value = state.title,
                    onValueChange = onTitleChange,
                    label = stringResource(id = R.string.enter_title),
                    isError = state.hasError
                )

                CustomOutlinedTextField(
                    value = state.description,
                    onValueChange = onDescriptionChange,
                    label = stringResource(id = R.string.enter_description),
                    isError = state.hasError,
                    isSingleLine = false,
                    maxLines = 4
                )

                CustomOutlinedTextField(
                    value = state.preparationTime,
                    onValueChange = onPreparationTimeChange,
                    label = stringResource(id = R.string.enter_preparation_time),
                    isError = state.hasError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = {
                        if (state.hasError) Text(text = stringResource(id = R.string.incorrect_format))
                        if (state.hasEmpty) Text(text = stringResource(id = R.string.empty_fields))
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Checkbox(
                        checked = state.isFavorite,
                        onCheckedChange = onFavoriteChange
                    )
                    Text(text = stringResource(id = R.string.mark_as_favorite))
                }

                CustomTextButton(
                    text = stringResource(id = R.string.add_recipe),
                    onClick = { onAddRecipe() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecipeFormScreen() {
    RecipeAppTheme {
        RecipeFormScreen(
            state = RecipeFormState(
                title = "",
                description = "",
                preparationTime = "",
                isFavorite = false,
                hasEmpty = false,
                successfulSave = false
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            onPreparationTimeChange = {},
            onFavoriteChange = {},
            onAddRecipe = {},
            onNavigationBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecipeFormEmptyScreen() {
    RecipeAppTheme {
        RecipeFormScreen(
            state = RecipeFormState(
                title = "",
                description = "",
                preparationTime = "",
                isFavorite = false,
                hasEmpty = true,
                successfulSave = false
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            onPreparationTimeChange = {},
            onFavoriteChange = {},
            onAddRecipe = {},
            onNavigationBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRecipeFormErrorScreen() {
    RecipeAppTheme {
        RecipeFormScreen(
            state = RecipeFormState(
                title = "",
                description = "",
                preparationTime = "",
                isFavorite = false,
                hasEmpty = false,
                hasError = true,
                successfulSave = false
            ),
            onTitleChange = {},
            onDescriptionChange = {},
            onPreparationTimeChange = {},
            onFavoriteChange = {},
            onAddRecipe = {},
            onNavigationBack = {}
        )
    }
}