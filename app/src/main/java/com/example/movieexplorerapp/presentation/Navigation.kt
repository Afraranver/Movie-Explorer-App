package com.example.movieexplorerapp.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieexplorerapp.presentation.dashboard.DashboardScreen
import com.example.movieexplorerapp.presentation.login.AuthScreen
import com.example.movieexplorerapp.presentation.login.LoginViewModel
import com.example.movieexplorerapp.presentation.movie_details.MovieDetailsScreen
import com.example.movieexplorerapp.presentation.movie_details.YoutubePlayerScreen
import com.example.movieexplorerapp.presentation.search_movie.SearchPageScreen
import com.example.movieexplorerapp.presentation.view_all.ViewAllScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val loginState = hiltViewModel<LoginViewModel>().loginState.value

    AnimatedNavHost(
        navController = navController,
        startDestination = if (loginState.sessionId == null) Screen.LoginScreen.route else Screen.Dashboard.route
    ) {
        // Login screen
        composable(route = Screen.AuthScreen.route) { backStackEntry ->
            // Pass the `isSignUp` parameter based on some logic or navigation arguments
            val isSignUp = backStackEntry.arguments?.getString("isSignUp")?.toBoolean() ?: false
            AuthScreen(
                isSignUp = isSignUp,
                navController = navController
            )
        }

        // Dashboard and other screens
        addDashboard(navController)
        addViewAllMovies(navController)
        addMovieDetails(navController)
        addYoutubePlayer(navController)
        addSearchPage(navController)
    }
}


private fun NavGraphBuilder.addDashboard(navController: NavController) {
    composable(route = Screen.Dashboard.route) {
        DashboardScreen(navController = navController)
    }

}

private fun NavGraphBuilder.addViewAllMovies(navController: NavController) {
    composable(
        route = Screen.ViewAll.route + "?moviesType={moviesType}",
        arguments = listOf(
            navArgument("moviesType") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        val moviesType = it.arguments?.getString("moviesType") ?: ""
        ViewAllScreen(navController = navController, moviesType)
    }
}

private fun NavGraphBuilder.addMovieDetails(navController: NavController) {
    composable(
        route = Screen.MovieDetailsScreen.route +
                "?movieId={movieId}&moviesTitle={moviesTitle}",
        arguments = listOf(
            navArgument("movieId") {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument("moviesTitle") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        val movieId = it.arguments?.getString("movieId") ?: ""
        val moviesTitle = it.arguments?.getString("moviesTitle") ?: ""
        MovieDetailsScreen(navController = navController, moviesTitle)
    }
}

private fun NavGraphBuilder.addYoutubePlayer(navController: NavController) {
    composable(
        route = Screen.YoutubePlayerScreen.route + "youtubeCode={youtubeCode}",
        arguments = listOf(
            navArgument("youtubeCode") {
                type = NavType.StringType
                defaultValue = ""
            }
        )
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