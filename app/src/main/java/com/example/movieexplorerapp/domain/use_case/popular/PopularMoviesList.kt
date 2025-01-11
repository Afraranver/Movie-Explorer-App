package com.example.movieexplorerapp.domain.use_case.popular

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.data.remote.dto.model.movies.PopularMovieResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

class PopularMoviesList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String, page: Int) :kotlinx.coroutines.flow.Flow<NetworkResult<PopularMovieResponse>> {
        return tmDbRepository.popularList(lang, page)
    }
}