package com.example.movieexplorerapp.domain.use_case.details

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.details.MovieDetailsResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import retrofit2.Response
import javax.inject.Inject

class MovieDetails @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String, movieId: String) :kotlinx.coroutines.flow.Flow<NetworkResult<Response<MovieDetailsResponse>>> {
        return tmDbRepository.movieDetails(lang, movieId)
    }
}