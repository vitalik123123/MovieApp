package com.example.fintech2023chupin.data.repositories.local.room

import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.local.MoviesLocalDataSource
import com.example.fintech2023chupin.data.repositories.local.room.dao.MoviesDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.MoviesEntity
import io.reactivex.rxjava3.core.Single

class RoomMoviesDataSource(private val moviesDao: MoviesDao) : MoviesLocalDataSource {

    override fun getAllLocalMovies(): Single<List<FilmTopResponse_films>> {
        return moviesDao.getAllLocalMovies().map { list ->
            list.map {
                it.toFilmTopResponse_films()
            }
        }
    }

    override suspend fun checkMovieLocal(film: FilmTopResponse_films) {
        if (!moviesDao.exists(film.id)) {
            moviesDao.saveMovieLocal(MoviesEntity.fromFilmTopResponse_films(film = film))
        }else{
            moviesDao.deleteMovieLocal(film.id)
        }
    }

    override suspend fun saveMovieLocal(film: FilmTopResponse_films) {
        moviesDao.saveMovieLocal(MoviesEntity.fromFilmTopResponse_films(film = film))
    }


}