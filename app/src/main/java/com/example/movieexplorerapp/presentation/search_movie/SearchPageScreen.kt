package com.example.movieexplorerapp.presentation.search_movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.dashboard.components.BottomNavigationBar
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.movie_details.components.SearchBar
import com.example.movieexplorerapp.presentation.search_movie.components.SearchEmpty
import com.example.movieexplorerapp.presentation.search_movie.components.SearchMovieItemCard

@Composable
fun SearchPageScreen(
    navController: NavController,
    viewModel: SearchPageViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Automatically request focus when screen is shown
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    // Cleanup focus when the screen is no longer in the composition
    DisposableEffect(Unit) {
        onDispose {
            // Clear the focus and hide the keyboard when leaving the screen
            focusManager.clearFocus()
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.focusRequester(focusRequester)
                    ) {
                        SearchBar(
                            onSearch = { state ->
                                state.value.text.let { query ->
                                    if (query.isNotBlank()) {
                                        viewModel.searchMovie(query)
                                    }
                                }
                                viewModel.searchMovie(state.value.text)
                            },
                            onCancel = { viewModel.clearSearch() }
                        )
                    }
                }
            }

            Box(modifier = Modifier.padding(top = 10.dp)) {
                SearchItemList(viewModel, navController)
                IsLoading(isLoading = viewModel.isLoading.value)
                ErrorView(viewModel.apiError.value)
                SearchEmpty(viewModel.listEmpty.value)
            }
        }
    }
}

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


