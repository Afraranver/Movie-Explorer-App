package com.example.movieexplorerapp.presentation.search_movie.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.search_movie.SearchPageViewModel

@Composable
fun SearchItemList(viewModel: SearchPageViewModel, navController: NavController) {
    LazyColumn() {
        items(
            items = viewModel.searchMoviePagingItems,
            key = { item ->
                item.movieId
            }
        ) { item ->
            SearchMovieItemCard(item, navController)
        }
    }
}