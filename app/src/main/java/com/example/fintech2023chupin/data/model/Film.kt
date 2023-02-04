package com.example.fintech2023chupin.data.model

import com.google.gson.annotations.SerializedName

data class Film(
    @SerializedName("kinopoiskId")
    var id: Int,

    @SerializedName("nameRu")
    var title: String,

    @SerializedName("posterUrl")
    var poster: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("countries")
    var countries: List<Country>,

    @SerializedName("genres")
    var genres: List<Genre>
)

data class Country(
    @SerializedName("country")
    var country: String
)
