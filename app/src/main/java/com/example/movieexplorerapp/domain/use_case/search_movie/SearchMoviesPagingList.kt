package com.example.movieexplorerapp.domain.use_case.search_movie

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.search_movies.SearchMovieResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SearchMoviesPagingList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(
        query: String,
        lang: String
    ): Flow<NetworkResult<Response<SearchMovieResponse>>> {
        return tmDbRepository.searchPagingList(query = query, lang = lang)
    }
}
