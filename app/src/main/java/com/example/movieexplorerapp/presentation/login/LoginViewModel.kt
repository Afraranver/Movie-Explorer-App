package com.example.movieexplorerapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    fun authenticate(username: String, password: String, isSignUp: Boolean = false) {
        viewModelScope.launch {
            _loginState.value = LoginState(isLoading = true)

            try {
                val authResult = if (isSignUp) {
                    firebaseAuth.createUserWithEmailAndPassword(username, password).await()
                } else {
                    firebaseAuth.signInWithEmailAndPassword(username, password).await()
                }
                val userId = authResult.user?.uid
                _loginState.value = LoginState(sessionId = userId)
            } catch (e: Exception) {
                _loginState.value = LoginState(error = e.localizedMessage ?: "Authentication failed")
            }
        }
    }
}