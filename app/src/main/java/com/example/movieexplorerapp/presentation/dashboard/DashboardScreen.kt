package com.example.movieexplorerapp.presentation.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.dashboard.components.BottomNavigationBar
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.dashboard.components.MovieItemCard
import com.example.movieexplorerapp.presentation.dashboard.components.MovieTitle
import com.example.movieexplorerapp.presentation.dashboard.components.TopBar

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) { paddingValues ->
        Box(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
            LazyColumn {
                item {
                    TopBar(navController, viewModel.popularMovieList.isNotEmpty())
                    Spacer(modifier = Modifier.height(20.dp))

                    MovieTitle(
                        navController,
                        viewModel.topRatedMovieList.isNotEmpty(),
                        MoviesType.TOP_RATED
                    )
                    TopRatedList(viewModel = viewModel, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))

                    MovieTitle(
                        navController,
                        viewModel.popularMovieList.isNotEmpty(),
                        MoviesType.POPULAR
                    )
                    PopularList(viewModel = viewModel, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))

                    MovieTitle(
                        navController,
                        viewModel.nowPlayingMovieList.isNotEmpty(),
                        MoviesType.NOW_PLAYING
                    )
                    NowPlayingList(viewModel = viewModel, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))

                    MovieTitle(
                        navController,
                        viewModel.upcomingMovieList.isNotEmpty(),
                        MoviesType.UPCOMING
                    )
                    UpcomingList(viewModel = viewModel, navController = navController)
                    Spacer(modifier = Modifier.height(5.dp))


                }
            }
            IsLoading(isLoading = viewModel.isLoading.containsValue(true))
            ErrorView(viewModel.apiError.value)
        }
    }
}


@Composable
fun PopularList(viewModel: DashboardViewModel, navController: NavController) {
    LazyRow(Modifier.padding(top = 10.dp)) {
        items(
            items = viewModel.popularMovieList,
            key = { item ->
                item.movieId
            }
        ) { item ->
            MovieItemCard(item, Modifier.width(140.dp), navController)
        }
    }
}

@Composable
fun NowPlayingList(viewModel: DashboardViewModel, navController: NavController) {
    LazyRow(Modifier.padding(top = 10.dp)) {
        items(
            items = viewModel.nowPlayingMovieList,
            key = { item ->
                item.movieId
            }
        ) { item ->
            MovieItemCard(item, Modifier.width(140.dp), navController)
        }
    }
}

@Composable
fun UpcomingList(viewModel: DashboardViewModel, navController: NavController) {
    LazyRow(Modifier.padding(top = 10.dp)) {
        items(
            items = viewModel.upcomingMovieList,
            key = { item ->
                item.movieId
            }
        ) { item ->
            MovieItemCard(item, Modifier.width(140.dp), navController)
        }
    }
}

@Composable
fun TopRatedList(viewModel: DashboardViewModel, navController: NavController) {
    LazyRow(Modifier.padding(top = 10.dp)) {
        items(
            items = viewModel.topRatedMovieList,
            key = { item ->
                item.movieId
            }
        ) { item ->
            MovieItemCard(item, Modifier.width(140.dp), navController)
        }
    }
}

