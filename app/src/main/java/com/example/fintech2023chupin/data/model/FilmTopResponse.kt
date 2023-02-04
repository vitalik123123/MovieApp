package com.example.fintech2023chupin.data.model

import com.google.gson.annotations.SerializedName

data class FilmTopResponse(
    @SerializedName("pagesCount")
    var page: Int,

    @SerializedName("films")
    var films: ArrayList<FilmTopResponse_films>
)

data class FilmTopResponse_films(
    @SerializedName("filmId")
    var id: Int,

    @SerializedName("nameRu")
    var title: String,

    @SerializedName("year")
    var year: String,

    @SerializedName("genres")
    var genres: List<Genre>,

    @SerializedName("posterUrlPreview")
    var poster: String,

    var stateInFavorite: Boolean = false
)

data class Genre(
    @SerializedName("genre")
    var genre: String
)
