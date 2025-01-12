package com.example.movieexplorerapp.presentation.auth
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.domain.model.User
import com.example.movieexplorerapp.domain.respository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = mutableStateOf(AuthState())
    val authState: State<AuthState> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = authRepository.signUp(email, password)
            _authState.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = authRepository.signIn(email, password)
            _authState.value = result
        }
    }

    fun checkAuthStatus() {
        _authState.value = AuthState(isAuthenticated = authRepository.isAuthenticated())
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthState(isAuthenticated = false)
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val userInfo = authRepository.getUserInfo()

            if (userInfo != null) {
                // Update the state with user info
                _authState.value = AuthState(user = userInfo)
            } else {
                // Handle error (optional)
                _authState.value = AuthState(error = "Failed to fetch user info")
            }
        }
    }
}