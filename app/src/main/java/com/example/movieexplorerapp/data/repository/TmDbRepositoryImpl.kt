package com.example.tmdbapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieexplorerapp.common.Constants
import com.example.tmdbapp.data.paging.*
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.common.ResponseCodeManager
import com.example.movieexplorerapp.data.remote.TMDbApiService
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
import com.example.movieexplorerapp.data.remote.dto.paging.NowPlayingPagingSource
import com.example.movieexplorerapp.data.remote.dto.paging.TopRatedPagingSource
import com.example.movieexplorerapp.data.remote.dto.paging.UpcomingPagingSource
import com.example.movieexplorerapp.domain.respository.TMDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class TmDbRepositoryImpl @Inject constructor(private val apiService: TMDbApiService) :
    TMDbRepository {
    override suspend fun getRequestToken(apiKey: String): RequestTokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionId(apiKey: String, requestToken: String): SessionResponse {
        TODO("Not yet implemented")
    }

    //paging
    override suspend fun popularPagingPagingList(lang: String): Flow<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = { PopularPagingSource(apiService, lang) }
    ).flow

    override suspend fun nowPlayingPagingList(
        lang: String
    ): Flow<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = { NowPlayingPagingSource(apiService, lang) }
    ).flow

    override suspend fun upcomingPagingList(lang: String): Flow<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = { UpcomingPagingSource(apiService, lang) }
    ).flow

    override suspend fun topRatedPagingList(lang: String): Flow<PagingData<MovieItem>> = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = { TopRatedPagingSource(apiService, lang) }
    ).flow


    //non-paging
    override suspend fun popularList(
        lang: String,
        page: Int
    ): Flow<NetworkResult<PopularMovieResponse>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getPopular(
                    page = page, language = lang
                )
                emit(
                    NetworkResult.Success(response)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun nowPlayingList(
        lang: String,
        page: Int
    ): Flow<NetworkResult<NowPlayingMovieResponse>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getNowPlaying(
                    page = page, language = lang
                )
                emit(
                    NetworkResult.Success(response)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun upcomingList(
        lang: String,
        page: Int
    ): Flow<NetworkResult<UpcomingMovieResponse>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getUpcoming(
                    page = page, language = lang
                )
                emit(
                    NetworkResult.Success(response)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun topRatedList(
        lang: String,
        page: Int
    ): Flow<NetworkResult<TopRatedMovieResponse>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getTopRated(
                    page = page, language = lang
                )
                emit(
                    NetworkResult.Success(response)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }


    override suspend fun movieDetails(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<MovieDetailsResponse>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getMovieDetails(
                    movieId = movieId, language = lang
                )
                emit(
                    if (response.isSuccessful) NetworkResult.Success(response) else
                        NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun movieCredits(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<MovieCreditsResponse>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getMovieCredits(
                    movieId = movieId, language = lang
                )
                emit(
                    if (response.isSuccessful) NetworkResult.Success(response) else
                        NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun getVideos(
        lang: String,
        movieId: String
    ): Flow<NetworkResult<Response<GetVideosResponse>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getVideos(
                    movieId = movieId, language = lang
                )
                emit(
                    if (response.isSuccessful) NetworkResult.Success(response) else
                        NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }

    override suspend fun searchPagingList(
        query: String,
        lang: String
    ): Flow<NetworkResult<Response<SearchMovieResponse>>> {
        return flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.searchMovie(query = query, language = lang, page = 1)
                emit(
                    if (response.isSuccessful) NetworkResult.Success(response) else
                        NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                )
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is HttpException -> {
                            NetworkResult.Failure(
                                false,
                                throwable.code(),
                                throwable.response()?.errorBody(),
                                throwable.response()
                                    ?.let { ResponseCodeManager.checkRetrofitApiResponse(it) })
                        }
                        is IOException -> {
                            NetworkResult.Failure(true, null, null, Constants.StatusMessages.DEFAULT)
                        }
                        else -> {
                            NetworkResult.Failure(false, null, null, Constants.Errors.CONVERSION_FAILURE)
                        }
                    }
                )
            }
        }.flowOn(Dispatchers.IO)

    }


}
