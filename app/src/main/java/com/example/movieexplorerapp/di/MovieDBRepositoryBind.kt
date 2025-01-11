package com.example.movieexplorerapp.di

import com.example.movieexplorerapp.domain.respository.TMDbRepository
import com.example.tmdbapp.data.repository.TmDbRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class TmDbRepositoryBind {

    @Binds
    @Singleton
    abstract fun bindRepository(tmDbRepositoryImpl: TmDbRepositoryImpl): TMDbRepository

}