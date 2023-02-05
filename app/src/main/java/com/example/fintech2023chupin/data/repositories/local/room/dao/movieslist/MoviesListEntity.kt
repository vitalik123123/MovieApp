package com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.model.Genre
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class MoviesListEntity(

    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "genres")
    val genres: List<Genre>,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "state")
    val state: Boolean = true
) {

    fun toFilmTopResponse_films(): FilmTopResponse_films = FilmTopResponse_films(
        id = id,
        title = title,
        year = year,
        genres = genres,
        poster = poster,
        stateInFavorite = state
    )

    companion object {
        const val TABLE_NAME = "movies_entity_table"

        fun fromFilmTopResponse_films(film: FilmTopResponse_films): MoviesListEntity = MoviesListEntity(
            id = film.id,
            title = film.title,
            year = film.year,
            genres = film.genres,
            poster = film.poster,
            state = true
        )
    }
}
