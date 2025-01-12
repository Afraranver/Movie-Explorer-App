package com.example.movieexplorerapp.presentation.movie_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieexplorerapp.BuildConfig
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.data.remote.dto.model.cast.CastItem

@Composable
fun ItemCastCard(castItem: CastItem?) {
    Card(
        modifier = Modifier
            .padding(start = 15.dp, bottom = 10.dp)
            .size(width = 140.dp, height = 220.dp) // Adjusted for better size
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        elevation = 6.dp // Added elevation for modern shadow
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f) // Image takes 70% of the card height
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(BuildConfig.LARGE_IMAGE_URL + castItem?.profilePath)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(id = R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 12.dp,
                                topEnd = 12.dp
                            )
                        ), // Rounded corners for the image
                    loading = {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_img_loading))
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                )

                // Subtle gradient overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                )
            }

            // Name and character details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f) // Text takes 30% of the card height
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White,
                                Color.LightGray
                            )
                        )
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = castItem?.name ?: "Unknown",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Text(
                    text = castItem?.character ?: "Unknown Role",
                    style = MaterialTheme.typography.body2.copy(color = Color.Gray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}