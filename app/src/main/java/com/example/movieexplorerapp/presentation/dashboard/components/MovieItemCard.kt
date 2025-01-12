package com.example.movieexplorerapp.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.presentation.Screen

@Composable
fun MovieItemCard(item: MovieItem?, modifier: Modifier, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                navController.navigate(Screen.MovieDetailsScreen.route + "?movieId=${item?.movieId.toString()}&moviesTitle=${item?.title}")
            }
            .background(MaterialTheme.colorScheme.surface), // Apply background color using modifier
        shape = RoundedCornerShape(12.dp), // Rounded corners for a modern look
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ) // Correct CardElevation type
    ) {
        Column(modifier = modifier) {
            // Image Loading with Shimmer Effect
            val shimmer = rememberShimmer()
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.ORIGINAL_IMAGE_URL + item?.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop, // Adjust content scale for better aspect ratio handling
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Make the image height a bit more prominent
                loading = {
                    // Apply shimmer effect as a placeholder
                    Box(
                        modifier = Modifier.fillMaxSize().background(brush = shimmer)
                    )
                }
            )

            Spacer(modifier = Modifier.height(12.dp)) // Added space between image and text

            // Title Text
            Text(
                text = item?.title ?: "",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.2.sp
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Release Date Text
            Text(
                text = item?.releaseDate ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) // Subtle color for secondary text
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun rememberShimmer() = Brush.horizontalGradient(
    colors = listOf(
        Color.LightGray.copy(alpha = 0.5f),
        Color.Gray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.5f)
    ),
    startX = 0f,
    endX = 1000f
)