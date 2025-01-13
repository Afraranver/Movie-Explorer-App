package com.example.movieexplorerapp.presentation.movie_details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.SavedStateHandle
import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.common.NetworkResult
import com.example.movieexplorerapp.data.remote.dto.model.cast.MovieCreditsResponse
import com.example.movieexplorerapp.data.remote.dto.model.details.MovieDetailsResponse
import com.example.movieexplorerapp.data.remote.dto.model.videos.GetVideosResponse
import com.example.movieexplorerapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    useCases: UseCases, savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _movieDetailsResponse: MutableState<MovieDetailsResponse> =
        mutableStateOf(MovieDetailsResponse())
    val movieDetailsResponse: State<MovieDetailsResponse> = _movieDetailsResponse

    private val _movieCreditsResponse: MutableState<MovieCreditsResponse> =
        mutableStateOf(MovieCreditsResponse())
    val movieCreditsResponse: State<MovieCreditsResponse> = _movieCreditsResponse

    private val _getVideosResponse: MutableState<GetVideosResponse> =
        mutableStateOf(GetVideosResponse())
    val getVideosResponse: State<GetVideosResponse> = _getVideosResponse

    private val _apiError = mutableStateOf(false)
    val apiError: State<Boolean> = _apiError

    private var _isLoading = mutableStateMapOf<Int, Boolean>()
    val isLoading: Map<Int, Boolean> = _isLoading

    init {
        initMapValues()
        savedStateHandle.get<String>("movieId")?.let { movieId ->
            if (movieId.isNotEmpty()) {
                viewModelScope.launch {
                    useCases.movieDetails.invoke(Constants.LANG, movieId).collect {
                        when (it) {
                            is NetworkResult.Success -> {
                                it.value.body()?.let { response ->
                                    _movieDetailsResponse.value = response
                                    delay(1000)
                                    _isLoading[0] = false
                                }
                            }

                            is NetworkResult.Failure -> {
                                _apiError.value = true
                                _isLoading[0] = false
                            }

                            is NetworkResult.Loading -> {

                            }
                        }

                    }
                    useCases.movieCredits.invoke(Constants.LANG, movieId).collect {
                        when (it) {

                            is NetworkResult.Success -> {
                                it.value.body()?.let { response ->
                                    _movieCreditsResponse.value = response
                                    delay(1000)
                                    _isLoading[1] = false
                                }

                            }

                            is NetworkResult.Failure -> {
                                _apiError.value = true
                                _isLoading[1] = false
                            }

                            is NetworkResult.Loading -> {

                            }
                        }

                    }
                    useCases.getVideos.invoke(Constants.LANG, movieId).collect {
                        when (it) {

                            is NetworkResult.Success -> {
                                it.value.body()?.let { response ->
                                    _getVideosResponse.value = response
                                    delay(1000)
                                    _isLoading[2] = false
                                }

                            }

                            is NetworkResult.Failure -> {
                                _apiError.value = true
                                _isLoading[2] = false
                            }

                            is NetworkResult.Loading -> {

                            }
                        }
                    }
                }
            }
        }
    }

    private fun initMapValues() {
        _isLoading[0] = true
        _isLoading[1] = true
        _isLoading[2] = true
    }

}