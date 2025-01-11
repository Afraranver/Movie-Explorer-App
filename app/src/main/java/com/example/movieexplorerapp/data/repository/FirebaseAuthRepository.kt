package com.example.movieexplorerapp.data.repository

import com.example.movieexplorerapp.domain.respository.AuthRepository
import com.example.movieexplorerapp.presentation.login.AuthState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): AuthState {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            AuthState(isAuthenticated = true) // User signed up successfully
        } catch (e: Exception) {
            AuthState(error = e.localizedMessage ?: "An error occurred during sign-up")
        }
    }

    override suspend fun signIn(email: String, password: String): AuthState {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthState(isAuthenticated = true) // User logged in successfully
        } catch (e: Exception) {
            AuthState(error = e.localizedMessage ?: "An error occurred during login")
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }
}