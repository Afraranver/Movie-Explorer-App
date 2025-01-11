package com.example.movieexplorerapp.data.remote.dto.model.movies
import com.google.gson.annotations.SerializedName

data class NowPlayingMovieResponse(

    @SerializedName("dates")
    val dates: Dates = Dates(),

    @SerializedName("page")
    val page: Int = 1,

    @SerializedName("total_pages")
    val totalPages: Int = 0,

    @SerializedName("results")
    val results: List<MovieItem> = emptyList(),

    @SerializedName("total_results")
    val totalResults: Int = 0
)


