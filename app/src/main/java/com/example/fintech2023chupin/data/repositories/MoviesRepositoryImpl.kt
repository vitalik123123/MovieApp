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

    override fun getMoviesRemote(): Observable<FilmTopResponse> =
        moviesRemoteDataSource.getMoviesRemote()

    override fun getMovieForId(id: Int): Observable<Film> = moviesRemoteDataSource.getMovieForId(id)

    override fun getMoviesLocal(): Single<List<FilmTopResponse_films>> =
        moviesLocalDataSource.getAllLocalMovies()

    override suspend fun checkMovieLocal(film: FilmTopResponse_films) =
        moviesLocalDataSource.checkMovieLocal(film)

    override suspend fun saveMovieLocal(film: FilmTopResponse_films) =
        moviesLocalDataSource.saveMovieLocal(film)

    override suspend fun createMovieDetailsLocal(film: Film) =
        moviesLocalDataSource.createMovieDetailsLocal(film)

    override fun getForIdMovieDetailsLocal(id: Int): Single<List<Film>> =
        moviesLocalDataSource.getForIdMovieDetailsLocal(id)

    override suspend fun deleteMovieDetailsLocal(id: Int) =
        moviesLocalDataSource.deleteMovieDetailsLocal(id)

    override suspend fun existsMovieDetailsLocal(id: Int): Boolean =
        moviesLocalDataSource.existsMovieDetailsLocal(id)

}