package com.example.movieexplorerapp.common

import com.example.movieexplorerapp.R


sealed class HomeBottomNavigation(val title: String, val icon: Int, val route: String) {

    data object Home : HomeBottomNavigation("Home", R.drawable.ic_home, "home")
    data object Explore : HomeBottomNavigation("Explore", R.drawable.baseline_explore_24, "explore")
    data object Profile : HomeBottomNavigation("Profile", R.drawable.ic_profile, "profile")

}