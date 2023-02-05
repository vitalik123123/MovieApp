package com.example.fintech2023chupin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails.MovieDetailsDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.moviedetails.MovieDetailsEntity
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.movieslist.MoviesListEntity

@Database(
    entities = [
        MoviesListEntity::class,
        MovieDetailsEntity::class
    ],
    version = 3
)
@TypeConverters(Converters::class)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesListDao

    abstract fun movieDetailsDao(): MovieDetailsDao
}