package com.example.movieexplorerapp.data.remote.dto

data class RequestTokenResponse(
    val expires_at: String,
    val request_token: String,
    val success: Boolean
)

data class SessionResponse(
    val success: Boolean,
    val session_id: String
)

data class ValidateResponse(
    val request_token: String
)