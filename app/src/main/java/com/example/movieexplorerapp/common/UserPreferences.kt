package com.example.movieexplorerapp.common

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    // Save login state (whether the user is logged in)
    fun saveLoginState(isLoggedIn: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    // Get login state (whether the user is logged in)
    fun getLoginState(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Clear login state (logout)
    fun clearLoginState() {
        sharedPreferences.edit().apply {
            remove(KEY_IS_LOGGED_IN)
            apply()
        }
    }
}