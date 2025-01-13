package com.example.movieexplorerapp.domain.use_case.now_playing

import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.movies.NowPlayingMovieResponse
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import javax.inject.Inject

class NowPlayingMoviesList @Inject constructor(private val tmDbRepository: TMDbRepository) {
    suspend operator fun invoke(lang: String, page: Int) :kotlinx.coroutines.flow.Flow<NetworkResult<NowPlayingMovieResponse>> {
        return tmDbRepository.nowPlayingList(lang, page)
    }
}