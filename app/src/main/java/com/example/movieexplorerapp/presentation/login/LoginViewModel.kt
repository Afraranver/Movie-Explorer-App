package com.example.movieexplorerapp.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.data.remote.TMDbApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: TMDbApiService
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun authenticate(username: String, password: String, apiKey: String) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)

            try {
                // Step 1: Get Request Token
                val tokenResponse = apiService.getRequestToken(apiKey)
                val token = tokenResponse.request_token

                // Step 2: Validate with Login
                val validateResponse = apiService.validateWithLogin(
                    apiKey, username, password, token
                )

                // Step 3: Create Session
                val sessionResponse = apiService.createSession(apiKey, validateResponse.request_token)

                // Update login state with session ID
                _loginState.value = LoginState(sessionId = sessionResponse.session_id)
            } catch (e: Exception) {
                // Update login state with error message
                _loginState.value = LoginState(error = e.localizedMessage ?: "An error occurred")
            }
        }
    }
}