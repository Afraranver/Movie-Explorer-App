package com.example.movieexplorerapp.presentation.movie_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieexplorerapp.data.remote.dto.model.cast.MovieCreditsResponse

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