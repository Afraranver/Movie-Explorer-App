package com.example.movieexplorerapp.presentation.view_all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.paging.PagingData
import com.example.movieexplorerapp.common.Constants
import com.example.movieexplorerapp.domain.model.movies.MovieItem
import com.example.movieexplorerapp.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@HiltViewModel
class ViewAllViewModel @Inject constructor(useCases: UseCases) :
    ViewModel() {

    var popularMoviesPagingItems: Flow<PagingData<MovieItem>> = emptyFlow()
    var nowPlayingMoviesPagingItems: Flow<PagingData<MovieItem>> = emptyFlow()
    var upcomingMoviesPagingItems: Flow<PagingData<MovieItem>> = emptyFlow()
    var topRatedMoviesPagingItems: Flow<PagingData<MovieItem>> = emptyFlow()


    init {
        viewModelScope.launch {

            popularMoviesPagingItems = useCases.popularMoviesPagingList.invoke(Constants.LANG)
            nowPlayingMoviesPagingItems = useCases.nowPlayingMoviesPagingList.invoke(Constants.LANG)
            upcomingMoviesPagingItems = useCases.upcomingMoviesPagingList.invoke(Constants.LANG)
            topRatedMoviesPagingItems = useCases.topRatedMoviesPagingList.invoke(Constants.LANG)

        }
    }
}