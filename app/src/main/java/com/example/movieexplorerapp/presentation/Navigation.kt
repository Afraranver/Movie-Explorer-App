package com.example.movieexplorerapp.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieexplorerapp.common.DataStoreRepository
import com.example.movieexplorerapp.common.UserPreferences
import com.example.movieexplorerapp.presentation.auth.AuthScreen
import com.example.movieexplorerapp.presentation.auth.AuthViewModel
import com.example.movieexplorerapp.presentation.auth.SignUpScreen
import com.example.movieexplorerapp.presentation.dashboard.DashboardScreen
import com.example.movieexplorerapp.presentation.movie_details.MovieDetailsScreen
import com.example.movieexplorerapp.presentation.movie_details.YoutubePlayerScreen
import com.example.movieexplorerapp.presentation.profile.ProfileScreen
import com.example.movieexplorerapp.presentation.profile.ProfileViewModel
import com.example.movieexplorerapp.presentation.search_movie.SearchPageScreen
import com.example.movieexplorerapp.presentation.view_all.ViewAllScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authViewModel = hiltViewModel<AuthViewModel>()
    val profileViewModel = hiltViewModel<ProfileViewModel>()

    // Create a DataStoreRepository instance
    val dataStoreRepository = DataStoreRepository(context)

    // Observe authentication state changes
    val authStateFlow = dataStoreRepository.isAuthenticated.collectAsState(initial = false)
    val isAuthenticated = authStateFlow.value

    // Start screen based on authentication state
//    val startDestination = if (isAuthenticated) Screen.Dashboard.route else Screen.AuthScreen.route

    // Observe and navigate based on authentication status
    LaunchedEffect(isAuthenticated) {
        // Optionally, check or perform actions based on auth status
    }

    val userPreferences = UserPreferences(context)
    val isLoggedIn = userPreferences.getLoginState()
    val startDestination = if (isLoggedIn) Screen.Dashboard.route else Screen.AuthScreen.route


    Scaffold { padding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(padding)
        ) {
            // Composables for navigation
            addAuthScreen(navController, authViewModel)
            addSignUpScreen(navController, authViewModel)
            addProfileScreen(navController, profileViewModel)
            addDashboardScreen(navController)

            // Specific routes
            addViewAllMovies(navController)
            addMovieDetails(navController)
            addYoutubePlayer(navController)
            addSearchPage(navController)
        }
    }
}

// Centralized composable route functions
private fun NavGraphBuilder.addAuthScreen(navController: NavController, viewModel: AuthViewModel) {
    composable(route = Screen.AuthScreen.route) {
        AuthScreen(navController = navController, viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addSignUpScreen(navController: NavController, viewModel: AuthViewModel) {
    composable(route = Screen.SignUpScreen.route) {
        SignUpScreen(navController = navController, viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    composable(route = Screen.ProfileScreen.route) {
        ProfileScreen(navController = navController, viewModel = viewModel)
    }
}

private fun NavGraphBuilder.addDashboardScreen(navController: NavController) {
    composable(route = Screen.Dashboard.route) {
        DashboardScreen(navController = navController)
    }
}

// Add more screens as needed
private fun NavGraphBuilder.addViewAllMovies(navController: NavController) {
    composable(
        route = Screen.ViewAll.route + "?moviesType={moviesType}",
        arguments = listOf(navArgument("moviesType") { type = NavType.StringType; defaultValue = "" })
    ) {
        val moviesType = it.arguments?.getString("moviesType") ?: ""
        ViewAllScreen(navController = navController, moviesType)
    }
}

private fun NavGraphBuilder.addMovieDetails(navController: NavController) {
    composable(
        route = Screen.MovieDetailsScreen.route + "?movieId={movieId}&moviesTitle={moviesTitle}",
        arguments = listOf(
            navArgument("movieId") { type = NavType.StringType; defaultValue = "" },
            navArgument("moviesTitle") { type = NavType.StringType; defaultValue = "" }
        )
    ) {
        val moviesTitle = it.arguments?.getString("moviesTitle") ?: ""
        MovieDetailsScreen(navController = navController, moviesTitle)
    }
}

private fun NavGraphBuilder.addYoutubePlayer(navController: NavController) {
    composable(
        route = Screen.YoutubePlayerScreen.route + "youtubeCode={youtubeCode}",
        arguments = listOf(navArgument("youtubeCode") { type = NavType.StringType; defaultValue = "" })
    ) {
        val youtubeCode = it.arguments?.getString("youtubeCode") ?: ""
        YoutubePlayerScreen(navController = navController, youtubeCode)
    }
}

private fun NavGraphBuilder.addSearchPage(navController: NavController) {
    composable(route = Screen.SearchPageScreen.route) {
        SearchPageScreen(navController = navController)
    }
}