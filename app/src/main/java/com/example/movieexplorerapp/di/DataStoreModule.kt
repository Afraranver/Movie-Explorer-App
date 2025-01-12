package com.example.movieexplorerapp.di

import android.content.Context
import com.example.movieexplorerapp.common.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }
}