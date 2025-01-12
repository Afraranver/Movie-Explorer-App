package com.example.movieexplorerapp.presentation.dashboard.components
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieexplorerapp.presentation.Screen

@Composable
fun SearchBar(navController: NavController) {
    var isPressed by remember { mutableStateOf(false) }

    // Animation for scaling effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    // Animation for elevation
    val animatedElevation = animateDpAsState(
        targetValue = if (isPressed) 2.dp else 8.dp,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .scale(scale) // Apply scaling animation
            .clip(shape = RoundedCornerShape(30.dp))
            .pointerInput(Unit) { // Detect press for animation
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // Wait for release
                        isPressed = false
                        navController.navigate(Screen.SearchPageScreen.route)
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = animatedElevation.value) // Correct elevation usage
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Search for a movie...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(start = 10.dp)
            )
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "search_icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FancySearchBar(navController: NavController) {
    val query by remember { mutableStateOf("") }
    var isPressed by remember { mutableStateOf(false) }

    // Animation for the search bar when focused
    val transition = updateTransition(targetState = query.isNotEmpty(), label = "Search Bar Transition")

    // Define the colors and shape for the search bar
    val backgroundColor by transition.animateColor(label = "BackgroundColor") {
        if (it) Color.Gray else Color.Black // Black background when focused
    }

    val iconColor by transition.animateColor(label = "IconColor") {
        if (it) Color.Black else Color.Gray // Yellow icon when focused, White when idle
    }

    val textColor by transition.animateColor(label = "TextColor") {
        if (it) Color.White else Color.Gray // White text when focused, Gray when idle
    }

    val scale by animateFloatAsState(
        targetValue = if (query.isNotEmpty()) 1.05f else 1f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(horizontal = 20.dp, vertical = 16.dp) // Margin on top and side padding
            .shadow(12.dp, shape = RoundedCornerShape(30.dp), clip = false)
            .background(backgroundColor, shape = RoundedCornerShape(30.dp))
            .scale(scale) // Apply scaling animation for hover effect
            .pointerInput(Unit) { // Detect tap for navigation
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease() // Wait for release
                        isPressed = false
                        navController.navigate(Screen.SearchPageScreen.route)
                    }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp) // Apply elevation for depth
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            // Search Text
            Text(
                text = query.ifEmpty { "Search for a movie..." },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textColor, // Inverted color based on focus
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .weight(1f) // Allow the text to take space
                    .padding(start = 10.dp)
            )

            // Search Icon
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_search),
                contentDescription = "search_icon",
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically),
                tint = iconColor
            )
        }
    }
}

@Preview
@Composable
fun SimpleComposablePreview() {
    val navController = rememberNavController()
    FancySearchBar(navController)
}