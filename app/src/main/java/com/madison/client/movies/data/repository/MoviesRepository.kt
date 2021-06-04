package com.madison.client.movies.data.repository

import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.model.MovieResponse
import com.madison.client.movies.data.repository.remote.MoviesRemoteDataSource
import io.reactivex.Single

open class MoviesRepository constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource
): BaseRepository() {

    fun getMovies(page: Int, sortBy: String? = null): Single<MovieResponse> {
        return moviesRemoteDataSource.getMovies(page, sortBy)
    }

    fun getMovieDetails(id: Int): Single<Movie> {
        return moviesRemoteDataSource.getMovieDetails(id)
    }
}