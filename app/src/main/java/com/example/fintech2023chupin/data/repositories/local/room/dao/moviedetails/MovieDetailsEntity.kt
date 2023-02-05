package com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fintech2023chupin.data.model.Country
import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.Genre
import com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails.MovieDetailsEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MovieDetailsEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "poster")
    var poster: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "countries")
    var countries: List<Country>,

    @ColumnInfo(name = "genres")
    var genres: List<Genre>
) {

    fun toFilm(): Film = Film(
        id = id,
        title = title,
        poster = poster,
        description = description,
        countries = countries,
        genres = genres
    )

    companion object {
        const val TABLE_NAME = "movie_details_entity_table"

        fun fromFilm(film: Film): MovieDetailsEntity = MovieDetailsEntity(
            id = film.id,
            title = film.title,
            poster = film.poster,
            description = film.description,
            countries = film.countries,
            genres = film.genres
        )
    }
}
