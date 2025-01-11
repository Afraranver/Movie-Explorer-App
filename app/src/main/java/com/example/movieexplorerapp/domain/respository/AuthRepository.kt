package com.example.movieexplorerapp.domain.respository

import com.example.movieexplorerapp.presentation.login.AuthState

interface AuthRepository {
    suspend fun signUp(email: String, password: String): AuthState
    suspend fun signIn(email: String, password: String): AuthState
    suspend fun signOut()
    fun isAuthenticated(): Boolean
}