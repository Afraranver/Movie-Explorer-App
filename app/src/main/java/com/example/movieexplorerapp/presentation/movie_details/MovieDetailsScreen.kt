package com.example.movieexplorerapp.presentation.movie_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.dashboard.components.ErrorView
import com.example.movieexplorerapp.presentation.dashboard.components.IsLoading
import com.example.movieexplorerapp.presentation.movie_details.components.ItemCast
import com.example.movieexplorerapp.presentation.movie_details.components.ItemOverview
import com.example.movieexplorerapp.presentation.movie_details.components.ItemPoster
import com.example.movieexplorerapp.presentation.movie_details.components.ItemTitle
import com.example.movieexplorerapp.presentation.view_all.components.ToolBar

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


