package com.example.fintech2023chupin.data.repositories.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface MoviesDao {

    @Query("SELECT * FROM ${MoviesEntity.TABLE_NAME}")
    fun getAllLocalMovies(): Single<List<MoviesEntity>>

    @Insert(entity = MoviesEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieLocal(movie: MoviesEntity)

    @Query("DELETE FROM ${MoviesEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteMovieLocal(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${MoviesEntity.TABLE_NAME} WHERE id = :id)")
    suspend fun exists(id: Int): Boolean
}