package com.example.movieexplorerapp.domain.model.cast

import com.google.gson.annotations.SerializedName

data class MovieCreditsResponse(
    @SerializedName("id")
	val id: Int = 0,

    @SerializedName("cast")
	val cast: List<CastItem> = emptyList(),

    @SerializedName("crew")
	val crew: List<CrewItem> = emptyList()
)

data class CrewItem(
    @SerializedName("id")
	val id: Int = 0,

    @SerializedName("gender")
	val gender: Gender = Gender.UNKNOWN,

    @SerializedName("credit_id")
	val creditId: String = "",

    @SerializedName("known_for_department")
	val knownForDepartment: String = "",

    @SerializedName("original_name")
	val originalName: String = "",

    @SerializedName("popularity")
	val popularity: Double = 0.0,

    @SerializedName("name")
	val name: String = "",

    @SerializedName("profile_path")
	val profilePath: String? = null,

    @SerializedName("adult")
	val isAdult: Boolean = false,

    @SerializedName("department")
	val department: String = "",

    @SerializedName("job")
	val job: String = ""
)

data class CastItem(
    @SerializedName("id")
	val id: Int = 0,

    @SerializedName("cast_id")
	val castId: Int = 0,

    @SerializedName("character")
	val character: String = "",

    @SerializedName("gender")
	val gender: Gender = Gender.UNKNOWN,

    @SerializedName("credit_id")
	val creditId: String = "",

    @SerializedName("known_for_department")
	val knownForDepartment: String = "",

    @SerializedName("original_name")
	val originalName: String = "",

    @SerializedName("popularity")
	val popularity: Double = 0.0,

    @SerializedName("name")
	val name: String = "",

    @SerializedName("profile_path")
	val profilePath: String? = null,

    @SerializedName("adult")
	val isAdult: Boolean = false,

    @SerializedName("order")
	val order: Int = 0
)

enum class Gender(val value: Int) {
	UNKNOWN(0),
	MALE(1),
	FEMALE(2);

	companion object {
		fun fromValue(value: Int): Gender = entries.find { it.value == value } ?: UNKNOWN
	}
}
