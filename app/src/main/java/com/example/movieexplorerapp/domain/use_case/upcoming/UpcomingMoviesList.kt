package com.example.movieexplorerapp.domain.use_case.upcoming

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.movies.UpcomingMovieResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

class UpcomingMoviesList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String, page: Int) :kotlinx.coroutines.flow.Flow<NetworkResult<UpcomingMovieResponse>> {
        return tmDbRepository.upcomingList(lang, page)
    }
}