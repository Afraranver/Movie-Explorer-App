package com.example.movieexplorerapp.presentation.auth

import com.example.movieexplorerapp.domain.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val token: String? = null,
    val user: User? = null
)