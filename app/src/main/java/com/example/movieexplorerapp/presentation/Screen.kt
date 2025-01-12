package com.example.movieexplorerapp.presentation

sealed class Screen(val route: String) {
    data object AuthScreen : Screen("auth_screen")
    data object SignUpScreen : Screen("signup_screen")
    data object Dashboard : Screen("dashboard_screen")
    data object ViewAll : Screen("view_all_screen")
    data object MovieDetailsScreen : Screen("movie_details_screen")
    data object YoutubePlayerScreen : Screen("youtube_player_screen")
    data object SearchPageScreen : Screen("search_page_screen")
}
