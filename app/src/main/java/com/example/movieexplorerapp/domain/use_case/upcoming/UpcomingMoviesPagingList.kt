package com.example.movieexplorerapp.domain.use_case.upcoming

import androidx.paging.PagingData
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

class UpcomingMoviesPagingList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String) :kotlinx.coroutines.flow.Flow<PagingData<MovieItem>> {
        return tmDbRepository.upcomingPagingList(lang)
    }
}