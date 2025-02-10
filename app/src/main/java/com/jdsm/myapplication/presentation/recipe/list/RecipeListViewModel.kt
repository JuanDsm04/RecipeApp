package com.jdsm.myapplication.presentation.recipe.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jdsm.myapplication.data.repository.LocalRecipeRepository
import com.jdsm.myapplication.di.AppDependencies
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val recipeRepository: LocalRecipeRepository
) : ViewModel() {
    private var getDataJob: Job? =null
    private val _state = MutableStateFlow(RecipeListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            if (recipeRepository.initialSync()) {
                getRecipes()
            }
        }
    }

    private fun getRecipes() {
        getDataJob?.cancel()
        getDataJob = viewModelScope.launch {
            val recipes = recipeRepository.getRecipes()

            _state.update { currentState ->
                val filterRecipes = recipes
                    .filter { !currentState.filterByFavorites || it.favorite }
                    .sortedBy { if (currentState.sortByTime) it.preparationTime else -it.preparationTime }

                currentState.copy(recipes = filterRecipes)
            }

        }
    }

    fun toggleFavoriteFilter() {
        _state.update { it.copy(filterByFavorites = !it.filterByFavorites) }
        getRecipes()
    }

    fun toggleSortByTime() {
        _state.update { it.copy(sortByTime = !it.sortByTime) }
        getRecipes()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = checkNotNull(this[APPLICATION_KEY])
                val appDatabase = AppDependencies.provideDatabase(context)
                RecipeListViewModel(
                    recipeRepository = LocalRecipeRepository(appDatabase.recipeDao())
                )
            }
        }
    }
}