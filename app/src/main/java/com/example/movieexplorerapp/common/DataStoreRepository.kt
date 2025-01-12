package com.example.movieexplorerapp.common

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.encryptedDataStore: DataStore<Preferences> by preferencesDataStore(name = "secure_user_prefs")

class DataStoreRepository(context: Context) {

    private val dataStore = context.encryptedDataStore

    // Define keys for preferences
    private object PreferencesKeys {
        val THEME = booleanPreferencesKey("theme_preference")
        val IS_AUTHENTICATED = booleanPreferencesKey("is_authenticated")
        val USER_TOKEN = stringPreferencesKey("user_token")
    }

    // Save theme preference
    suspend fun saveThemePreference(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = isDark
        }
        Log.d("DataStoreRepository", "Theme preference saved: $isDark") // Log the saved value
    }

    // Get theme preference
    suspend fun getThemePreference(): Boolean {
        val preferences = dataStore.data.first()
        val theme = preferences[PreferencesKeys.THEME] ?: false // Default to light theme
        Log.d("DataStoreRepository", "Theme preference fetched: $theme") // Log the fetched value
        return theme
    }

    // Save authentication status and token
    suspend fun saveSession(isAuthenticated: Boolean, token: String?) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_AUTHENTICATED] = isAuthenticated
            preferences[PreferencesKeys.USER_TOKEN] = token ?: ""
        }
        Log.d("DataStoreRepository", "Session saved: isAuthenticated = $isAuthenticated, token = ${token ?: "null"}") // Log saved session data
    }

    // Get authentication status
    val isAuthenticated: Flow<Boolean> = dataStore.data
        .map { preferences ->
            val isAuth = preferences[PreferencesKeys.IS_AUTHENTICATED] ?: false
            Log.d("DataStoreRepository", "Authentication status fetched: $isAuth") // Log the fetched authentication status
            isAuth
        }

    // Get the user token
    val userToken: Flow<String> = dataStore.data
        .map { preferences ->
            val token = preferences[PreferencesKeys.USER_TOKEN] ?: ""
            Log.d("DataStoreRepository", "User token fetched: $token") // Log the fetched token
            token
        }
}