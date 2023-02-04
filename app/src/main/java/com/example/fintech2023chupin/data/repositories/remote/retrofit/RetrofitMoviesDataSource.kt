package com.example.fintech2023chupin.data.repositories.remote.retrofit

import com.example.fintech2023chupin.data.api.MoviesApi
import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse
import com.example.fintech2023chupin.data.repositories.remote.MoviesRemoteDataSource
import io.reactivex.rxjava3.core.Observable

class RetrofitMoviesDataSource(private val moviesApi: MoviesApi) : MoviesRemoteDataSource {

    override fun getMoviesRemote(): Observable<FilmTopResponse> =
        moviesApi.getMoviesList("TOP_100_POPULAR_FILMS")

    override fun getMovieForId(id: Int): Observable<Film> =
        moviesApi.getMovieDetails(id)

}