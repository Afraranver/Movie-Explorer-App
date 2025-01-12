package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.common.DataStoreRepository
import com.example.movieexplorerapp.data.repository.FirebaseAuthRepositoryImpl
import com.example.movieexplorerapp.domain.respository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        dataStoreRepository: DataStoreRepository,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return FirebaseAuthRepositoryImpl(firebaseAuth, dataStoreRepository, firestore)
    }
}