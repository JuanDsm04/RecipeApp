package com.jdsm.myapplication.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jdsm.myapplication.data.local.entity.RecipeEntity

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: RecipeEntity)

    @Insert
    suspend fun insertAll(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM RecipeEntity")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity
}