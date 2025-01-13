package com.example.movieexplorerapp.domain.model.details

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(

    @SerializedName("original_language")
    val originalLanguage: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("video")
    val isVideo: Boolean = false,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("revenue")
    val revenue: Int = 0,

    @SerializedName("genres")
    val genres: List<Genre> = emptyList(),

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry> = emptyList(),

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("vote_count")
    val voteCount: Int = 0,

    @SerializedName("budget")
    val budget: Int = 0,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("original_title")
    val originalTitle: String? = null,

    @SerializedName("runtime")
    val runtime: Int = 0,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage> = emptyList(),

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany> = emptyList(),

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,

    @SerializedName("tagline")
    val tagline: String? = null,

    @SerializedName("adult")
    val isAdult: Boolean = false,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("status")
    val status: String? = null
)

data class ProductionCompany(

    @SerializedName("logo_path")
    val logoPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("origin_country")
    val originCountry: String? = null
)

data class BelongsToCollection(

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("poster_path")
    val posterPath: String? = null
)

data class Genre(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: Int = 0
)

data class SpokenLanguage(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null,

    @SerializedName("english_name")
    val englishName: String? = null
)

data class ProductionCountry(

    @SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @SerializedName("name")
    val name: String? = null
)
