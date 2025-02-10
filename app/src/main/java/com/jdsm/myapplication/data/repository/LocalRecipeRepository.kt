package com.jdsm.myapplication.data.repository

import com.jdsm.myapplication.data.local.dao.RecipeDao
import com.jdsm.myapplication.data.local.entity.mapToEntity
import com.jdsm.myapplication.data.local.entity.mapToModel
import com.jdsm.myapplication.data.model.Recipe
import com.jdsm.myapplication.data.source.RecipeDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class LocalRecipeRepository (
    private val recipeDao: RecipeDao
) {
    suspend fun initialSync(): Boolean {
        return try {
            if (recipeDao.getAllRecipes().isEmpty()) {
                val recipeDb = RecipeDb()
                val recipesToInsert = recipeDb.getAllRecipes().map{ it.mapToEntity() }
                recipeDao.insertAll(recipesToInsert)
            }
            true

        } catch (e: Exception) {
            coroutineContext.ensureActive()
            println(e)
            false
        }
    }

    suspend fun getRecipes(): List<Recipe> {
        return recipeDao.getAllRecipes().map { it.mapToModel() }
    }

    suspend fun getRecipeById(id: Int): Recipe {
        return recipeDao.getRecipeById(id).mapToModel()
    }
}