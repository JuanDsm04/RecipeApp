package com.jdsm.myapplication.data.local

import android.content.Context
import androidx.room.Room

object AppDatabaseFactory {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase{
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder (
                context.applicationContext,
                AppDatabase::class.java,
                "recipeApp.db"

            ).fallbackToDestructiveMigration().build()
            INSTANCE = instance
            instance
        }
    }
}