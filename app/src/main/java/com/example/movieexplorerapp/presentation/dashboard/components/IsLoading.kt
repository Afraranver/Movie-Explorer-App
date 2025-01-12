package com.example.movieexplorerapp.presentation.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movieexplorerapp.R

@Composable
fun IsLoading(isLoading: Boolean) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_loading))

    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(tween(durationMillis = 300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f))
                .blur(8.dp)
        ) {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .align(Alignment.Center)  // Centered alignment
                    .size(150.dp)  // Fixed size for smooth animation display
            )
        }
    }
}