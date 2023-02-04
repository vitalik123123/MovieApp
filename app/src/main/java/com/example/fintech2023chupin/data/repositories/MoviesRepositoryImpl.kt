package com.example.fintech2023chupin.data.repositories

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.local.MoviesLocalDataSource
import com.example.fintech2023chupin.data.repositories.remote.MoviesRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override fun getMoviesRemote(): Observable<FilmTopResponse> = moviesRemoteDataSource.getMoviesRemote()


    override fun getMovieForId(id: Int): Observable<Film> = moviesRemoteDataSource.getMovieForId(id)

    override fun getMoviesLocal(): Single<List<FilmTopResponse_films>> = moviesLocalDataSource.getAllLocalMovies()

    override suspend fun checkMovieLocal(film: FilmTopResponse_films) = moviesLocalDataSource.checkMovieLocal(film)

    override suspend fun saveMovieLocal(film: FilmTopResponse_films) = moviesLocalDataSource.saveMovieLocal(film)

}