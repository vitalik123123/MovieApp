package com.example.fintech2023chupin.data.repositories.local

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import io.reactivex.rxjava3.core.Single

interface MoviesLocalDataSource {

    fun getAllLocalMovies(): Single<List<FilmTopResponse_films>>

    suspend fun checkMovieLocal(film: FilmTopResponse_films)

    suspend fun saveMovieLocal(film: FilmTopResponse_films)

    suspend fun createMovieDetailsLocal(film: Film)

    fun getForIdMovieDetailsLocal(id: Int): Single<List<Film>>

    suspend fun deleteMovieDetailsLocal(id: Int)

    suspend fun existsMovieDetailsLocal(id: Int): Boolean

}