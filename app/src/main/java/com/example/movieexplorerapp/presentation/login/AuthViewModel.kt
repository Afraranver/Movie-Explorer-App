package com.example.movieexplorerapp.presentation.login
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    // Sign Up method
    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = authRepository.signUp(email, password)
            _authState.value = result
        }
    }

    // Sign In method
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            val result = authRepository.signIn(email, password)
            _authState.value = result
        }
    }

    // Check if user is authenticated
    fun checkAuthStatus() {
        _authState.value = AuthState(isAuthenticated = authRepository.isAuthenticated())
    }

    // Sign Out method
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _authState.value = AuthState(isAuthenticated = false)
        }
    }
}