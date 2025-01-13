package com.example.movieexplorerapp.domain.model.movies

import com.example.movieexplorerapp.domain.model.movies.Dates
import com.example.movieexplorerapp.domain.model.movies.MovieItem
import com.google.gson.annotations.SerializedName

data class UpcomingMovieResponse(

    @field:SerializedName("dates")
    val dates: Dates? = null,

    @field:SerializedName("page")
    val page: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("results")
    val results: List<MovieItem>? = null,

    @field:SerializedName("total_results")
    val totalResults: Int? = null
)