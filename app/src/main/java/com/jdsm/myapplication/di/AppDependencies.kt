package com.jdsm.myapplication.di

import android.content.Context
import com.jdsm.myapplication.data.local.AppDatabase
import com.jdsm.myapplication.data.local.AppDatabaseFactory

object AppDependencies {
    fun provideDatabase(context: Context): AppDatabase {
        return AppDatabaseFactory.getInstance(context )
    }
}