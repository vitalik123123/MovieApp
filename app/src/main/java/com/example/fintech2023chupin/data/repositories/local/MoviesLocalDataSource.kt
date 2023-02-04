package com.example.fintech2023chupin.data.repositories.local

import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import io.reactivex.rxjava3.core.Single

interface MoviesLocalDataSource {

    fun getAllLocalMovies(): Single<List<FilmTopResponse_films>>

    suspend fun checkMovieLocal(film: FilmTopResponse_films)

    suspend fun saveMovieLocal(film: FilmTopResponse_films)

}