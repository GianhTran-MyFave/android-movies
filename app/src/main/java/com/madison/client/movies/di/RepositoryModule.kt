package com.madison.client.movies.di

import com.madison.client.movies.data.repository.MoviesRepository
import com.madison.client.movies.data.repository.remote.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideMoviesRepository(
            moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepository(moviesRemoteDataSource)
    }
}