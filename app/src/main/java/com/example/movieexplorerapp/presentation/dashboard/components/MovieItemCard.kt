package com.example.movieexplorerapp.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

@Composable
fun MovieItemCard(item: MovieItem?, modifier: Modifier, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .background(color = Color.White)
            .clickable {
                navController.navigate(Screen.MovieDetailsScreen.route + "?movieId=${item?.movieId.toString()}&moviesTitle=${item?.title}")
            },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(BuildConfig.ORIGINAL_IMAGE_URL + item?.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.description),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                loading = {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_img_loading))
                    LottieAnimation(
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            val lineHeight = MaterialTheme.typography.displaySmall.fontSize * 4 / 3
            Text(
                text = item?.title ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 10.dp),
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                lineHeight = lineHeight
            )
            Text(
                text = item?.releaseDate ?: "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

    }

}