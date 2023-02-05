package com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface MoviesListDao {

    @Query("SELECT * FROM ${MoviesListEntity.TABLE_NAME}")
    fun getAllLocalMovies(): Single<List<MoviesListEntity>>

    @Insert(entity = MoviesListEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieLocal(movie: MoviesListEntity)

    @Query("DELETE FROM ${MoviesListEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteMovieLocal(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${MoviesListEntity.TABLE_NAME} WHERE id = :id)")
    suspend fun exists(id: Int): Boolean
}