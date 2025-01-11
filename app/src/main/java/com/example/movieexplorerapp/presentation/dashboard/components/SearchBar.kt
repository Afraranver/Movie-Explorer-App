package com.example.movieexplorerapp.presentation.dashboard.components
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.Screen

@Composable
fun SearchBar(navController: NavController) {
    var isPressed by remember { mutableStateOf(false) } // Track animation state

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