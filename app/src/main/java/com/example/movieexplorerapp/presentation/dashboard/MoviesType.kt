package com.example.movieexplorerapp.presentation.dashboard

sealed class MoviesType(val value: String) {
    data object POPULAR : MoviesType("Popular")
    data object NOW_PLAYING : MoviesType("Now Playing")
    data object UPCOMING : MoviesType("Upcoming")
    data object TOP_RATED : MoviesType("Top Rated")
}