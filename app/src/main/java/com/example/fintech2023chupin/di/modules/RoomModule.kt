package com.example.fintech2023chupin.di.modules

import android.content.Context
import androidx.room.Room
import com.example.fintech2023chupin.data.database.MoviesRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): MoviesRoomDatabase =
        Room.databaseBuilder(context, MoviesRoomDatabase::class.java, "database_movies")
            .fallbackToDestructiveMigration().build()
}