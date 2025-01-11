package com.example.movieexplorerapp.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.dashboard.components.MovieItemCard
import com.example.movieexplorerapp.presentation.dashboard.components.TopBar
import com.example.movieexplorerapp.common.HomeBottomNavigation
import com.example.movieexplorerapp.R

@Composable
fun DashboardScreen(navController: NavController, viewModel: DashboardViewModel = hiltViewModel()) {
    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) { paddingValues ->
        Box(modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())) {
            LazyColumn() {
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
fun BottomNavigationBar(navController: NavController) {
    val navigationItems = listOf(
        HomeBottomNavigation.Home,
        HomeBottomNavigation.Favorite,
        HomeBottomNavigation.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Theme-based colors
    val backgroundColor = MaterialTheme.colorScheme.surface
    val contentColor = MaterialTheme.colorScheme.onSurface
    val selectedColor = MaterialTheme.colorScheme.primary

    BottomNavigation(
        modifier = Modifier.shadow(elevation = 8.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = 8.dp
    ) {
        navigationItems.forEach { item ->
            val isSelected = item.route == currentRoute

            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) selectedColor.copy(alpha = 0.2f)
                                    else Color.Transparent
                                )
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                tint = if (isSelected) selectedColor else contentColor
                            )
                        }
                        if (isSelected) {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = selectedColor,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            )
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
                style = MaterialTheme.typography.bodySmall,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.clickable(
                    onClick = {
                        navController.navigate(Screen.ViewAll.route + "?moviesType=${moviesType.value}")
                    },
                )
            ) {
                Text(
                    text = "View all",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 10.dp),
                )
                Icon(
                    painter = painterResource(id = R.drawable.double_arrow_right_14214),
                    contentDescription = "arrow_forward",
                    Modifier.size(10.dp),
                    tint = Color.Gray
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
                item.movieId.toString()
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

