package com.example.movieexplorerapp.domain.respository

import androidx.paging.PagingData
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.data.remote.dto.RequestTokenResponse
import com.example.movieexplorerapp.data.remote.dto.SessionResponse
import com.example.movieexplorerapp.data.remote.dto.model.cast.MovieCreditsResponse
import com.example.movieexplorerapp.data.remote.dto.model.details.MovieDetailsResponse
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.data.remote.dto.model.movies.NowPlayingMovieResponse
import com.example.movieexplorerapp.data.remote.dto.model.movies.PopularMovieResponse
import com.example.movieexplorerapp.data.remote.dto.model.movies.TopRatedMovieResponse
import com.example.movieexplorerapp.data.remote.dto.model.movies.UpcomingMovieResponse
import com.example.movieexplorerapp.data.remote.dto.model.search_movies.SearchMovieResponse
import com.example.movieexplorerapp.data.remote.dto.model.videos.GetVideosResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TMDbRepository {
    //TODO
    suspend fun getRequestToken(apiKey: String): RequestTokenResponse
    //TODO
    suspend fun getSessionId(apiKey: String, requestToken: String): SessionResponse

    //paging
    suspend fun popularPagingPagingList(lang: String): Flow<PagingData<MovieItem>>
    suspend fun nowPlayingPagingList(lang: String): Flow<PagingData<MovieItem>>
    suspend fun upcomingPagingList(lang: String): Flow<PagingData<MovieItem>>
    suspend fun topRatedPagingList(lang: String): Flow<PagingData<MovieItem>>

    //non-paging
    suspend fun popularList(lang: String, page: Int): Flow<NetworkResult<PopularMovieResponse>>
    suspend fun nowPlayingList(
        lang: String,
        page: Int
    ): Flow<NetworkResult<NowPlayingMovieResponse>>

    suspend fun upcomingList(lang: String, page: Int): Flow<NetworkResult<UpcomingMovieResponse>>
    suspend fun topRatedList(lang: String, page: Int): Flow<NetworkResult<TopRatedMovieResponse>>

    suspend fun movieDetails(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<MovieDetailsResponse>>>

    suspend fun movieCredits(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<MovieCreditsResponse>>>

    suspend fun getVideos(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<GetVideosResponse>>>

    suspend fun searchPagingList(query: String, lang: String): Flow<NetworkResult<Response<SearchMovieResponse>>>
}