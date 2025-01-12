package com.example.movieexplorerapp.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieexplorerapp.common.HomeBottomNavigation
import com.example.movieexplorerapp.presentation.Screen

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navigationItems = listOf(
        HomeBottomNavigation.Home,
        HomeBottomNavigation.Explore,
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
                    when (item) {
                        is HomeBottomNavigation.Home -> {
                            // Navigate to Dashboard
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Dashboard.route) { inclusive = true }
                            }
                        }
                        is HomeBottomNavigation.Profile -> {
                            // Navigate to Profile
                            navController.navigate(Screen.ProfileScreen.route) {
                                popUpTo(HomeBottomNavigation.Profile.route) { inclusive = true }
                            }
                        }
                        else -> {
                            navController.navigate(Screen.SearchPageScreen.route) {
                                popUpTo(HomeBottomNavigation.Explore.route) { inclusive = true }
                            }
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