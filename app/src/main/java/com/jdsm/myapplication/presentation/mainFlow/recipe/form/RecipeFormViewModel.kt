package com.jdsm.myapplication.presentation.mainFlow.recipe.form

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jdsm.myapplication.data.local.AppDatabaseFactory
import com.jdsm.myapplication.data.model.Recipe
import com.jdsm.myapplication.data.repository.LocalRecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeFormViewModel (
    private val repository: LocalRecipeRepository

): ViewModel() {
    private val _state = MutableStateFlow(RecipeFormState())
    val state = _state.asStateFlow()

    fun onEvent(event: RecipeFormEvent){
        when (event){
            is RecipeFormEvent.TitleChange -> onTitleChange(event.title)
            is RecipeFormEvent.DescriptionChange -> onDescriptionChange(event.description)
            is RecipeFormEvent.PreparationTimeChange -> onPreparationTimeChange(event.preparationTime)
            is RecipeFormEvent.FavoriteChange -> onFavoriteChange(event.isFavorite)
            is RecipeFormEvent.ImageSelected -> {
                _state.value = state.value.copy(imagePath = event.imagePath)
            }
            RecipeFormEvent.AddRecipe -> onAddRecipe()
        }
    }

    private fun onTitleChange(title: String){
        _state.update { state ->
            state.copy(
                title = title
            )
        }
    }

    private fun onDescriptionChange(description: String) {
        _state.update { state ->
            state.copy(
                description = description
            )
        }
    }

    private fun onPreparationTimeChange(preparationTime: String){
        _state.update { state ->
            state.copy(
                preparationTime = preparationTime
            ) }
    }

    private fun onFavoriteChange(isFavorite: Boolean) {
        _state.update { state ->
            state.copy(
                isFavorite = isFavorite
            )
        }
    }

    private fun onImageSelected(imagePath: String) {
        _state.update { state ->
            state.copy(
                imagePath = imagePath
            )
        }
    }

    private fun onAddRecipe() {
        viewModelScope.launch {
            val currentState = state.value

            if (currentState.title.isBlank() || currentState.description.isBlank() || currentState.preparationTime.isBlank()) {
                _state.update {
                    it.copy(
                        hasEmpty = true,
                        hasError = false,
                        successfulSave = false
                    )
                }
                return@launch
            }

            val preparationTimeInt = currentState.preparationTime.toIntOrNull()
            if (preparationTimeInt == null || preparationTimeInt <= 0) {
                _state.update {
                    it.copy(
                        hasError = true,
                        hasEmpty = false,
                        successfulSave = false
                    )
                }
                return@launch
            }

            try {
                val recipe = Recipe(
                    id = 0,
                    title = currentState.title.trim(),
                    description = currentState.description.trim(),
                    preparationTime = preparationTimeInt,
                    favorite = currentState.isFavorite,
                    imagePath = currentState.imagePath
                )

                repository.insertRecipe(recipe)
                _state.update {
                    it.copy(
                        successfulSave = true,
                        hasError = false,
                        hasEmpty = false,
                        title = "",
                        description = "",
                        preparationTime = "",
                        isFavorite = false
                    )
                }

            } catch (e: Exception){
                _state.update {
                    it.copy(
                        hasError = true,
                        successfulSave = false
                    )
                }
            }
        }

    }

    companion object {
        fun Factory(context: Context ): ViewModelProvider.Factory {
            return viewModelFactory {
                initializer {
                    val database = AppDatabaseFactory.getInstance(context)
                    val recipeDao = database.recipeDao()
                    val repository = LocalRecipeRepository(recipeDao )

                    RecipeFormViewModel(repository)
                }
            }
        }
    }
}