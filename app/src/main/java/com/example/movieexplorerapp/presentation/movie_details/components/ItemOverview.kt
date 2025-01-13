package com.example.movieexplorerapp.presentation.movie_details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieexplorerapp.domain.model.details.MovieDetailsResponse

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