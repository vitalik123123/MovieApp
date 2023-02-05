package com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface MovieDetailsDao {

    @Insert(entity = MovieDetailsEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun createMovieDetailsLocal(movie: MovieDetailsEntity)

    @Query("SELECT * FROM ${MovieDetailsEntity.TABLE_NAME} WHERE id = :id")
    fun getForIdMovieDetailsLocal(id: Int): Single<List<MovieDetailsEntity>>

    @Query("DELETE FROM ${MovieDetailsEntity.TABLE_NAME} WHERE id = :id")
    suspend fun deleteMovieDetailsLocal(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM ${MovieDetailsEntity.TABLE_NAME} WHERE id = :id)")
    suspend fun exists(id: Int): Boolean
}