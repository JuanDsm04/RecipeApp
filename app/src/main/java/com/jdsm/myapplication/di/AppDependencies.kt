package com.jdsm.myapplication.di

import android.content.Context
import com.jdsm.myapplication.data.local.AppDatabase
import com.jdsm.myapplication.data.local.AppDatabaseFactory

object AppDependencies {
    private var database: AppDatabase? = null

    fun provideDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            database ?: AppDatabaseFactory.create(context).also {  database = it }
        }
    }
}