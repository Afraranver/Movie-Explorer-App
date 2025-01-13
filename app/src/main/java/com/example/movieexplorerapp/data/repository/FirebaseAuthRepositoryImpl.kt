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
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val user = firebaseAuth.currentUser
            val idToken = user?.getIdToken(true)?.await()?.token

            user?.let {
                val userDoc = firestore.collection("users").document(it.uid).get().await()

                if (!userDoc.exists()) {
                    val userData = hashMapOf(
                        "name" to "",
                        "email" to email,
                        "photoUrl" to user.photoUrl.toString()
                    )

                    firestore.collection("users").document(it.uid).set(userData).await()
                }
            }

            idToken?.let {
                dataStoreRepository.saveSession(true, it)
            }
            AuthState(isAuthenticated = true, error = null)

        } catch (e: Exception) {
            AuthState(isAuthenticated = false, error = e.message)
        }
    }

    override suspend fun signIn(email: String, password: String): AuthState {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()

            val user = firebaseAuth.currentUser
            val idToken = user?.getIdToken(true)?.await()?.token

            user?.let {
                val userDoc = firestore.collection("users").document(it.uid).get().await()

                if (!userDoc.exists()) {
                    val userData = hashMapOf(
                        "name" to "",
                        "email" to email,
                        "photoUrl" to user.photoUrl.toString()
                    )

                    firestore.collection("users").document(it.uid).set(userData).await()
                }
            }

            idToken?.let {
                dataStoreRepository.saveSession(true, it)
            }
            AuthState(isAuthenticated = true, error = null)
        } catch (e: Exception) {
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
                val userDoc = firestore.collection("users").document(uid).get().await()

                if (userDoc.exists()) {
                    val name = userDoc.getString("name") ?: ""
                    val email = userDoc.getString("email") ?: user.email ?: ""
                    val photoUrl = userDoc.getString("photoUrl")

                    User(name, email, photoUrl)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}