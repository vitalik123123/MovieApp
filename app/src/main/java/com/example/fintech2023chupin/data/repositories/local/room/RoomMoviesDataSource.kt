package com.example.fintech2023chupin.data.repositories.local.room

import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.local.MoviesLocalDataSource
import com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails.MovieDetailsDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails.MovieDetailsEntity
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomMoviesDataSource(
    private val moviesListDao: MoviesListDao,
    private val movieDetailsDao: MovieDetailsDao
) : MoviesLocalDataSource {

    override fun getAllLocalMovies(): Single<List<FilmTopResponse_films>> {
        return moviesListDao.getAllLocalMovies().map { list ->
            list.map {
                it.toFilmTopResponse_films()
            }
        }
    }

    override suspend fun checkMovieLocal(film: FilmTopResponse_films) {
        if (!moviesListDao.exists(film.id)) {
            moviesListDao.saveMovieLocal(MoviesListEntity.fromFilmTopResponse_films(film = film))
        } else {
            moviesListDao.deleteMovieLocal(film.id)
            deleteMovieDetailsLocal(film.id)
        }
    }

    override suspend fun saveMovieLocal(film: FilmTopResponse_films) {
        moviesListDao.saveMovieLocal(MoviesListEntity.fromFilmTopResponse_films(film = film))
    }

    override suspend fun createMovieDetailsLocal(film: Film) {
        movieDetailsDao.createMovieDetailsLocal(MovieDetailsEntity.fromFilm(film = film))
    }

    override fun getForIdMovieDetailsLocal(id: Int): Single<List<Film>> {
        return movieDetailsDao.getForIdMovieDetailsLocal(id).map { list ->
            list.map {
                it.toFilm()
            }
        }
    }

    override suspend fun deleteMovieDetailsLocal(id: Int) {
        movieDetailsDao.deleteMovieDetailsLocal(id)
    }

    override suspend fun existsMovieDetailsLocal(id: Int): Boolean {
        return movieDetailsDao.exists(id)
    }

}