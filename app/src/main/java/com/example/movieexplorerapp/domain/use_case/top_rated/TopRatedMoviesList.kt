package com.example.movieexplorerapp.domain.use_case.top_rated

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.data.remote.dto.model.movies.TopRatedMovieResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TopRatedMoviesList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String, page: Int) : Flow<NetworkResult<TopRatedMovieResponse>> {
        return tmDbRepository.topRatedList(lang, page)
    }
}