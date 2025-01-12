package com.example.movieexplorerapp.presentation.search_movie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.presentation.dashboard.components.FancySearchBar

@Composable
fun SearchMovieItemCard(item: MovieItem?, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp) // Added horizontal padding for more balanced spacing
            .clickable {
                navController.navigate(Screen.MovieDetailsScreen.route + "?movieId=${item?.movieId.toString()}&moviesTitle=${item?.title}")
            }
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp), // Rounded corners for a smoother look
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ) // Adding elevation for a subtle shadow
         // Use MaterialTheme surface for consistent color
    ) {
        Row(modifier = Modifier.padding(16.dp)) { // Increased padding around the Row for better spacing
            // Image with loading animation and error handling
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.ORIGINAL_IMAGE_URL + item?.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.Crop, // Better fit for images
                modifier = Modifier
                    .width(120.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp)), // Clip the image into rounded corners
                loading = {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_img_loading))
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                },
                error = {
                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = "Image error",
                        modifier = Modifier.align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            )
            Spacer(modifier = Modifier.width(16.dp)) // Space between image and text column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                // Movie Title
                Text(
                    text = item?.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // Movie Release Date
                Text(
                    text = item?.releaseDate ?: "Unknown Release Date",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Movie Overview (with truncated text and improved line height)
                Text(
                    text = item?.overview ?: "No overview available.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 20.sp
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Optionally, you can add a separator line
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))

                // Optional: Add a footer or action button (like 'View Details') if needed
            }
        }
    }
}
