package com.example.fintech2023chupin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintech2023chupin.data.repositories.local.room.dao.MoviesDao
import com.example.fintech2023chupin.data.repositories.local.room.dao.MoviesEntity

@Database(
    entities = [
        MoviesEntity::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class MoviesRoomDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}