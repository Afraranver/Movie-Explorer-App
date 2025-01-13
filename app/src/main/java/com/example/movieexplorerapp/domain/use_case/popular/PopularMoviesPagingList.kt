package com.example.movieexplorerapp.domain.use_case.popular

import androidx.paging.PagingData
import com.example.movieexplorerapp.domain.model.movies.MovieItem
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

class PopularMoviesPagingList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String) :kotlinx.coroutines.flow.Flow<PagingData<MovieItem>> {
        return tmDbRepository.popularPagingPagingList(lang)
    }
}