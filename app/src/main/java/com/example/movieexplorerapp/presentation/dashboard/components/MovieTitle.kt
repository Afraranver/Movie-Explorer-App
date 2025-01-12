package com.example.movieexplorerapp.presentation.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.dashboard.MoviesType

@Composable
fun MovieTitle(
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
                style = MaterialTheme.typography.titleMedium.copy(
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
                    .padding(end = 12.dp)
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