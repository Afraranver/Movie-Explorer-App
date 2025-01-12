package com.example.movieexplorerapp.domain.respository

import com.example.movieexplorerapp.domain.model.User
import com.example.movieexplorerapp.presentation.auth.AuthState

interface AuthRepository {
    suspend fun signUp(email: String, password: String): AuthState
    suspend fun signIn(email: String, password: String): AuthState
    suspend fun signOut()
    fun isAuthenticated(): Boolean
    suspend fun getUserInfo(): User?
}