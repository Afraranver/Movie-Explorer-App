package com.example.movieexplorerapp.presentation.search_movie

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.dashboard.components.BottomNavigationBar
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.movie_details.components.SearchBar
import com.example.movieexplorerapp.presentation.search_movie.components.SearchEmpty
import com.example.movieexplorerapp.presentation.search_movie.components.SearchItemList

@Composable
fun SearchPageScreen(
    navController: NavController,
    viewModel: SearchPageViewModel = hiltViewModel()
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    DisposableEffect(Unit) {
        onDispose {
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
            // Search Card with Elevation and Animation
            AnimatedVisibility(visible = true) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(10.dp, shape = RoundedCornerShape(30.dp)) // Add shadow for depth
                        .background(MaterialTheme.colorScheme.surface) // Card background
                        .animateContentSize() // Animate size changes
                        .clickable {
                            // Optionally add click event
                        },
                    shape = RoundedCornerShape(30.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ) // Add elevation for depth effect
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
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
            }

            // Animated loading, error, and empty states
            Box(modifier = Modifier.padding(top = 10.dp)) {
                // Search item list
                SearchItemList(viewModel, navController)


                // Error View with animation
                androidx.compose.animation.AnimatedVisibility(visible = viewModel.apiError.value) {
                    ErrorView(viewModel.apiError.value)
                }

                // Empty State with animation and style
                androidx.compose.animation.AnimatedVisibility(visible = viewModel.listEmpty.value) {
                    SearchEmpty(viewModel.listEmpty.value)
                }
            }
        }
    }
}




