package com.example.fintech2023chupin.data.repositories

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse
import io.reactivex.rxjava3.core.Observable

interface MoviesRepository {

    fun getMoviesRemote(): Observable<FilmTopResponse>

    fun getMovieForId(id: Int): Observable<Film>
}