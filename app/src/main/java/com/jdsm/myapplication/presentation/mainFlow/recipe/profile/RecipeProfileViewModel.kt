package com.jdsm.myapplication.presentation.mainFlow.recipe.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jdsm.myapplication.data.model.Recipe
import com.jdsm.myapplication.data.repository.LocalRecipeRepository
import com.jdsm.myapplication.di.AppDependencies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeProfileViewModel(
    private val recipeRepository: LocalRecipeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle["recipeId"])
    private val _state: MutableStateFlow<RecipeProfileState> = MutableStateFlow(RecipeProfileState())
    val state = _state.asStateFlow()

    init {
        getRecipeData()
    }

    private fun getRecipeData() {
        viewModelScope.launch {
            val recipe = recipeRepository.getRecipeById(recipeId)
            _state.update { state ->
                state.copy(data = recipe)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            state.value.data?.let { recipe ->
                val updatedRecipe = recipe.copy(favorite = !recipe.favorite)
                recipeRepository.updateRecipe(updatedRecipe)
                _state.update { it.copy(data = updatedRecipe) }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val context = checkNotNull(this[APPLICATION_KEY])
                val appDatabase = AppDependencies.provideDatabase(context)
                RecipeProfileViewModel(
                    recipeRepository = LocalRecipeRepository(
                        recipeDao = appDatabase.recipeDao()
                    ),
                    savedStateHandle = savedStateHandle
                )
            }
        }
    }
}
