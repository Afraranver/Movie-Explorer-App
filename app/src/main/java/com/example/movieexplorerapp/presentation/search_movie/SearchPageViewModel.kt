package com.example.movieexplorerapp.presentation.search_movie

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.domain.model.movies.MovieItem
import com.example.movieexplorerapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(val useCases: UseCases) : ViewModel() {

    private var _searchMoviePagingItems = mutableStateListOf<MovieItem>()
    val searchMoviePagingItems: List<MovieItem> = _searchMoviePagingItems

    private val _apiError = mutableStateOf(false)
    val apiError: State<Boolean> = _apiError

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _listEmpty = mutableStateOf(false)
    val listEmpty: State<Boolean> = _listEmpty

    fun searchMovie(query: String) {
        println("searchMovie: $query")
        viewModelScope.launch {
            useCases.searchMoviesPagingList.invoke(query, Constants.LANG).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        // Ensure response.body()?.results is a list of objects that can be mapped to MovieItem
                        val results = result.value.body()?.results
                        _searchMoviePagingItems.clear()
                        if (results.isNullOrEmpty()) {
                            _listEmpty.value = true
                        } else {
                            _listEmpty.value = false
                            // Here we assume results are in the correct format that can be mapped to MovieItem
                            _searchMoviePagingItems.addAll(results.map { movie ->
                                MovieItem(
                                    movieId = movie.movieId,
                                    title = movie.title,
                                    overview = movie.overview,
                                    posterPath = movie.posterPath,
                                    releaseDate = movie.releaseDate
                                )
                            })
                        }
                        _isLoading.value = false
                    }

                    is NetworkResult.Failure -> {
                        _apiError.value = true
                        _isLoading.value = false
                        _listEmpty.value = false
                    }

                    is NetworkResult.Loading -> {
                        _isLoading.value = true
                        _listEmpty.value = false
                    }
                }
            }
        }
    }

    fun clearSearch() {
        _searchMoviePagingItems.clear()
        _listEmpty.value = false
    }
}
