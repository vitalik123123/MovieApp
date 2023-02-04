package com.example.fintech2023chupin.di.modules

import com.example.fintech2023chupin.data.api.MoviesApi
import com.example.fintech2023chupin.data.repositories.MoviesRepository
import com.example.fintech2023chupin.data.repositories.MoviesRepositoryImpl
import com.example.fintech2023chupin.data.repositories.remote.MoviesRemoteDataSource
import com.example.fintech2023chupin.data.repositories.remote.retrofit.RetrofitMoviesDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MoviesDataModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(moviesApi: MoviesApi): MoviesRemoteDataSource =
        RetrofitMoviesDataSource(moviesApi = moviesApi)

    @Provides
    @Singleton
    fun provideMoviesRepository(
        remote: MoviesRemoteDataSource
    ): MoviesRepository = MoviesRepositoryImpl(remote)
}