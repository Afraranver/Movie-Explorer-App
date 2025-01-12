package com.example.movieexplorerapp.data.repository

import com.example.movieexplorerapp.common.DataStoreRepository
import com.example.movieexplorerapp.domain.model.User
import com.example.movieexplorerapp.domain.respository.AuthRepository
import com.example.movieexplorerapp.presentation.auth.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val dataStoreRepository: DataStoreRepository,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signUp(email: String, password: String): AuthState {
        return try {
            // Sign up with Firebase
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            // After successful sign up, get the ID token
            val user = firebaseAuth.currentUser
            val idToken = user?.getIdToken(true)?.await()?.token

            // Check if user data exists in Firestore
            user?.let {
                val userDoc = firestore.collection("users").document(it.uid).get().await()

                if (!userDoc.exists()) {
                    // Save user data if it doesn't exist
                    val userData = hashMapOf(
                        "name" to "", // Empty string initially, can be updated later
                        "email" to email,
                        "photoUrl" to user.photoUrl.toString() // or null if you don't have a photo
                    )

                    // Save user data to Firestore (users collection)
                    firestore.collection("users").document(it.uid).set(userData).await()
                }
            }

            // Save the authentication status and token securely
            idToken?.let {
                dataStoreRepository.saveSession(true, it)
            }
            // Return successful AuthState
            AuthState(isAuthenticated = true, error = null)

        } catch (e: Exception) {
            // Handle sign up errors
            AuthState(isAuthenticated = false, error = e.message)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthState {
        return try {
            // Sign in with Firebase
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            // After successful sign in, get the ID token
            val user = firebaseAuth.currentUser
            val idToken = user?.getIdToken(true)?.await()?.token

            // Check if user data exists in Firestore
            user?.let {
                val userDoc = firestore.collection("users").document(it.uid).get().await()

                if (!userDoc.exists()) {
                    // Save user data if it doesn't exist
                    val userData = hashMapOf(
                        "name" to "", // Empty string initially, can be updated later
                        "email" to email,
                        "photoUrl" to user.photoUrl.toString() // or null if you don't have a photo
                    )

                    // Save user data to Firestore (users collection)
                    firestore.collection("users").document(it.uid).set(userData).await()
                }
            }

            // Save the authentication status and token securely
            idToken?.let {
                dataStoreRepository.saveSession(true, it)
            }
            // Return successful AuthState
            AuthState(isAuthenticated = true, error = null)
        } catch (e: Exception) {
            // Handle sign in errors
            AuthState(isAuthenticated = false, error = e.message)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override fun isAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun getUserInfo(): User? {
        val user = firebaseAuth.currentUser
        return user?.uid?.let { uid ->
            try {
                // Fetch user data from Firestore using UID
                val userDoc = firestore.collection("users").document(uid).get().await()

                // Assuming that the document contains fields like "name", "email"
                if (userDoc.exists()) {
                    val name = userDoc.getString("name") ?: ""
                    val email = userDoc.getString("email") ?: user.email ?: ""
                    val photoUrl = userDoc.getString("photoUrl")

                    // Create a User object with the information
                    User(name, email, photoUrl)
                } else {
                    null // No user data found
                }
            } catch (e: Exception) {
                null // Handle errors fetching user data
            }
        }
    }

}