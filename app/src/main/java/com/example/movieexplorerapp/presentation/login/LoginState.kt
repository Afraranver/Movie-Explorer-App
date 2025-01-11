package com.example.movieexplorerapp.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val sessionId: String? = null,
    val error: String? = null
)
