package com.example.flightsearch.di

import android.content.Context
import com.example.flightsearch.data.AppDatabase
import com.example.flightsearch.data.UserPreferencesRepository

interface AppContainer {
    val userPreferencesRepository: UserPreferencesRepository
    val database: AppDatabase
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context)
    }

    override val database: AppDatabase by lazy {
        AppDatabase.getDatabase(context)
    }
}