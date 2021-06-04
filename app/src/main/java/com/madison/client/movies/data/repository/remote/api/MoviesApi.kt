package com.madison.client.movies.data.repository.remote.api

import com.madison.client.movies.BuildConfig
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("discover/movie")
    fun getMovies(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int?,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Single<Movie>
}