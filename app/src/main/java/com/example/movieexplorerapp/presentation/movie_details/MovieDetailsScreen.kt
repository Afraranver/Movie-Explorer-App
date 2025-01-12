package com.example.movieexplorerapp.presentation.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.data.remote.dto.model.cast.MovieCreditsResponse
import com.example.movieexplorerapp.data.remote.dto.model.details.MovieDetailsResponse
import com.example.movieexplorerapp.data.remote.dto.model.videos.GetVideosResponse
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.movie_details.components.CircularProgress
import com.example.movieexplorerapp.presentation.movie_details.components.ItemCastCard
import com.example.movieexplorerapp.presentation.view_all.components.ToolBar
import com.example.movieexplorerapp.common.formattedYear

@Composable
fun MovieDetailsScreen(
    navController: NavController, title: String, viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ToolBar(title = title, onBack = {
                navController.popBackStack()
            })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            val details = viewModel.movieDetailsResponse.value
            val cast = viewModel.movieCreditsResponse.value
            val videos = viewModel.getVideosResponse.value

            if (details.id != null && cast.id != null) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item { ItemPoster(details) }
                    item { ItemTitle(navController, details, videos) }
                    item { ItemOverview(details) }
                    item { ItemCast(cast) }
                }
            }

            // Loading and Error State
            IsLoading(isLoading = viewModel.isLoading.containsValue(true))
            ErrorView(viewModel.apiError.value)
        }
    }
}

@Composable
fun ItemPoster(response: MovieDetailsResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        // Background Image with Gradient Overlay
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.ORIGINAL_IMAGE_URL + response.backdropPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
            )
        }

        // Poster Image
        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.CenterStart)
                .offset(x = 16.dp) // Adjust offset to align the poster cleanly
                .clip(shape = RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .shadow(12.dp, RoundedCornerShape(16.dp))
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.ORIGINAL_IMAGE_URL + response.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(id = R.string.description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ItemTitle(navController: NavController, response: MovieDetailsResponse, videos: GetVideosResponse) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title = response.title ?: ""
        val year = formattedYear(response.releaseDate) ?: ""

        Text(
            text = buildAnnotatedString {
                append(title)
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append("($year)")
                }
            },
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            response.genres.forEach {
                it.name?.let { it1 -> Chip(label = it1) }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Circular Progress with User Score
                Column(
                    modifier = Modifier.padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgress((response.voteAverage.toFloat().div(10)))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "User Score",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                // Divider with padding
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                )

                // Play Trailer Icon Button
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            val trailer = videos.results?.find { it?.type == "Trailer" }
                            trailer?.key?.let { navController.navigate(Screen.YoutubePlayerScreen.route + "youtubeCode=$it") }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Trailer",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Play Trailer",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun ItemOverview(response: MovieDetailsResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = response.overview ?: "",
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 22.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun ItemCast(credits: MovieCreditsResponse) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Top Billed Cast",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            credits.cast.forEach {
                item { ItemCastCard(it) }
            }
        }
    }
}

@Composable
fun Chip(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}


