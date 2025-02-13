package com.jdsm.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jdsm.myapplication.data.local.dao.RecipeDao
import com.jdsm.myapplication.data.local.entity.RecipeEntity

@Database(
    entities = [
        RecipeEntity::class
    ],
    version = 3
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}