package com.example.movieexplorerapp.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreRepository(private val context: Context) {

    // Define keys for theme preference
    private object PreferencesKeys {
        val THEME = booleanPreferencesKey("theme_preference")
    }

    // Save theme preference
    suspend fun saveThemePreference(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = isDark
        }
    }

    // Get theme preference
    suspend fun getThemePreference(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[PreferencesKeys.THEME] ?: false // Default to light theme
    }
}