package com.example.fintech2023chupin.data.repositories

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse
import com.example.fintech2023chupin.data.repositories.remote.MoviesRemoteDataSource
import io.reactivex.rxjava3.core.Observable

class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
) : MoviesRepository {

    override fun getMoviesRemote(): Observable<FilmTopResponse> = moviesRemoteDataSource.getMoviesRemote()


    override fun getMovieForId(id: Int): Observable<Film> = moviesRemoteDataSource.getMovieForId(id)
}