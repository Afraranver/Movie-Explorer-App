package com.example.movieexplorerapp.presentation.dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.domain.use_case.UseCases
import com.example.movieexplorerapp.domain.use_case.now_playing.NowPlayingMoviesList
import com.example.movieexplorerapp.domain.use_case.popular.PopularMoviesList
import com.example.movieexplorerapp.domain.use_case.top_rated.TopRatedMoviesList
import com.example.movieexplorerapp.domain.use_case.upcoming.UpcomingMoviesList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(useCases: UseCases) : ViewModel() {

    private var _popularMovieList = mutableStateListOf<MovieItem>()
    val popularMovieList: List<MovieItem> = _popularMovieList

    private var _nowPlayingMovieList = mutableStateListOf<MovieItem>()
    val nowPlayingMovieList: List<MovieItem> = _nowPlayingMovieList

    private var _upcomingMovieList = mutableStateListOf<MovieItem>()
    val upcomingMovieList: List<MovieItem> = _upcomingMovieList

    private var _topRatedMovieList = mutableStateListOf<MovieItem>()
    val topRatedMovieList: List<MovieItem> = _topRatedMovieList

    private val _apiError = mutableStateOf(false)
    val apiError: State<Boolean> = _apiError

    private var _isLoading = mutableStateMapOf<Int, Boolean>()
    val isLoading: Map<Int, Boolean> = _isLoading

    init {
        viewModelScope.launch {
            val popularMoviesDeferred = async { loadMoviesList(useCases.popularMoviesList, _popularMovieList, 0) }
            val nowPlayingMoviesDeferred = async { loadMoviesList(useCases.nowPlayingMoviesList, _nowPlayingMovieList, 1) }
            val upcomingMoviesDeferred = async { loadMoviesList(useCases.upcomingMoviesList, _upcomingMovieList, 2) }
            val topRatedMoviesDeferred = async { loadMoviesList(useCases.topRatedMoviesList, _topRatedMovieList, 3) }

            awaitAll(popularMoviesDeferred, nowPlayingMoviesDeferred, upcomingMoviesDeferred, topRatedMoviesDeferred)
        }
    }

    private suspend fun loadMoviesList(
        moviesListUseCase: PopularMoviesList,
        movieList: MutableList<MovieItem>,
        index: Int
    ) {
        moviesListUseCase(Constants.LANG, 1).collect {
            when (it) {
                is NetworkResult.Success -> {
                    movieList.clear()
                    it.value.results?.forEach { result ->
                        movieList.add(result)
                    }
                    _isLoading[index] = false
                }
                is NetworkResult.Failure -> {
                    _apiError.value = true
                    _isLoading[index] = false
                }
                is NetworkResult.Loading -> {
                    _isLoading[index] = true
                }
            }
        }
    }

    private suspend fun loadMoviesList(
        moviesListUseCase: NowPlayingMoviesList,
        movieList: MutableList<MovieItem>,
        index: Int
    ) {
        moviesListUseCase(Constants.LANG, 1).collect {
            when (it) {
                is NetworkResult.Success -> {
                    movieList.clear()
                    it.value.results.forEach { result ->
                        movieList.add(result)
                    }
                    _isLoading[index] = false
                }
                is NetworkResult.Failure -> {
                    _apiError.value = true
                    _isLoading[index] = false
                }
                is NetworkResult.Loading -> {
                    _isLoading[index] = true
                }
            }
        }
    }

    private suspend fun loadMoviesList(
        moviesListUseCase: UpcomingMoviesList,
        movieList: MutableList<MovieItem>,
        index: Int
    ) {
        moviesListUseCase(Constants.LANG, 1).collect {
            when (it) {
                is NetworkResult.Success -> {
                    movieList.clear()
                    it.value.results?.forEach { result ->
                        movieList.add(result)
                    }
                    _isLoading[index] = false
                }
                is NetworkResult.Failure -> {
                    _apiError.value = true
                    _isLoading[index] = false
                }
                is NetworkResult.Loading -> {
                    _isLoading[index] = true
                }
            }
        }
    }

    private suspend fun loadMoviesList(
        moviesListUseCase: TopRatedMoviesList,
        movieList: MutableList<MovieItem>,
        index: Int
    ) {
        moviesListUseCase(Constants.LANG, 1).collect {
            when (it) {
                is NetworkResult.Success -> {
                    movieList.clear()
                    it.value.results?.forEach { result ->
                        movieList.add(result)
                    }
                    _isLoading[index] = false
                }
                is NetworkResult.Failure -> {
                    _apiError.value = true
                    _isLoading[index] = false
                }
                is NetworkResult.Loading -> {
                    _isLoading[index] = true
                }
            }
        }
    }
}
