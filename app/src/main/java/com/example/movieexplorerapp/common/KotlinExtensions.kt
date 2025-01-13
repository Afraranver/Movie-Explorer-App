package com.example.movieexplorerapp.common

import com.example.movieexplorerapp.data.local.entity.MovieEntity
import com.example.movieexplorerapp.data.remote.dto.model.movies.MovieItem
import com.example.movieexplorerapp.data.remote.dto.model.search_movies.SearchMovieResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun formattedYear(releaseDate: String?): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val outputFormat = SimpleDateFormat("yyyy", Locale("en"))
    val formattedDate = releaseDate?.let {
        try {
            val date = inputFormat.parse(it)
            val formattedDate = date?.let { it1 -> outputFormat.format(it1) }
            formattedDate
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }
    return formattedDate
}

fun minuteToTime(min: Int): String {
    var minute = min
    var hour = minute / 60
    minute %= 60
    hour %= 12
    return (if (hour < 10) "$hour" else hour).toString() + "h " + (if (minute < 10) "0$minute" else minute) + "m"
}

fun MovieEntity.toDomainModel() = posterPath?.let {
    MovieItem(
        movieId = id.toString(),
        title = title,
        overview = overview,
        posterPath = it,
        releaseDate = releaseDate
    )
}

fun MovieItem.toEntity() = MovieEntity(
    id = movieId.toIntOrNull() ?: 0,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate
)

fun convertCachedMoviesToSearchMovieResponse(movies: List<MovieEntity>): SearchMovieResponse {
    val results = movies.map {
        MovieItem(
            movieId = it.id.toString(),
            title = it.title,
            overview = it.overview,
            posterPath = it.posterPath.orEmpty(),
            releaseDate = it.releaseDate
        )
    }
    return SearchMovieResponse(results = results)
}