package com.example.movieexplorerapp.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.dashboard.components.BottomNavigationBar
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.dashboard.components.MovieItemCard
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

                    Title(
                        navController,
                        viewModel.popularMovieList.isNotEmpty(),
                        MoviesType.POPULAR
                    )

                    PopularList(viewModel = viewModel, navController = navController)

                    Spacer(modifier = Modifier.height(5.dp))
                    Title(
                        navController,
                        viewModel.nowPlayingMovieList.isNotEmpty(),
                        MoviesType.NOW_PLAYING
                    )
                    NowPlayingList(viewModel = viewModel, navController = navController)

                    Spacer(modifier = Modifier.height(5.dp))
                    Title(
                        navController,
                        viewModel.upcomingMovieList.isNotEmpty(),
                        MoviesType.UPCOMING
                    )
                    UpcomingList(viewModel = viewModel, navController = navController)

                    Spacer(modifier = Modifier.height(5.dp))
                    Title(
                        navController,
                        viewModel.topRatedMovieList.isNotEmpty(),
                        MoviesType.TOP_RATED
                    )
                    TopRatedList(viewModel = viewModel, navController = navController)
                }
            }
            IsLoading(isLoading = viewModel.isLoading.containsValue(true))
            ErrorView(viewModel.apiError.value)
        }
    }
}


@Composable
fun Title(
    navController: NavController,
    visibilty: Boolean?,
    moviesType: MoviesType
) {
    if (visibilty == true) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = moviesType.value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold, // Bold text to make it more prominent
                    letterSpacing = 0.5.sp // Adding some letter spacing for better readability
                ),
                color = MaterialTheme.colorScheme.onSurface, // Use the text color for the current theme
                modifier = Modifier
                    .padding(vertical = 8.dp) // Add vertical padding for better spacing
                    .clickable {
                        // You can add a click listener here if you want to handle clicks on this text
                        // For example, navigate to a detailed page or toggle the display.
                    },
                maxLines = 1, // Ensures the text doesn't overflow
                overflow = TextOverflow.Ellipsis // Adds an ellipsis if the text overflows
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clickable {
                        navController.navigate(Screen.ViewAll.route + "?moviesType=${moviesType.value}")
                    }
            ) {
                Text(
                    text = "View all",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold, // Makes the text bold for emphasis
                        letterSpacing = 0.15.sp // Adds a slight letter spacing for clarity
                    ),
                    color = MaterialTheme.colorScheme.primary, // Using the primary color for better integration with theme
                    modifier = Modifier.padding(end = 6.dp) // Adjust padding between text and icon
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "Arrow icon",
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
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

