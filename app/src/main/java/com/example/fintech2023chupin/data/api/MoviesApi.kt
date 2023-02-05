package com.example.fintech2023chupin.data.api

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/api/v2.2/films/top")
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    fun getMoviesList(@Query("type") type: String): Observable<FilmTopResponse>

    @GET("/api/v2.2/films/{id}")
    @Headers("X-API-KEY: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    fun getMovieDetails(@Path("id") id: Int): Observable<Film>

}