package com.madison.client.movies.data.repository.remote

import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.model.MovieResponse
import com.madison.client.movies.data.repository.remote.api.MoviesApi
import io.reactivex.Single
import javax.inject.Inject

open class MoviesRemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) : BaseRemoteDataSource() {

    fun getMovies(page: Int, sortBy: String?): Single<MovieResponse> {
        return moviesApi.getMovies(page, sortBy)
    }

    fun getMovieDetails(id: Int): Single<Movie> {
        return moviesApi.getMovieDetails(id)
    }
}