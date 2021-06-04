package com.madison.client.movies.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.madison.client.movies.BuildConfig
import com.madison.client.movies.data.repository.remote.api.MoviesApi
import com.madison.client.movies.data.repository.remote.api.middleware.AuthInterceptor
import com.madison.client.movies.data.repository.remote.api.service.ServiceGenerator
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    @Singleton
    @Provides
    fun provideMoviesApi(
            gson: Gson,
            authInterceptor: AuthInterceptor,
            loggingInterceptor: HttpLoggingInterceptor
    ): MoviesApi {
        val interceptors = arrayOf(authInterceptor, loggingInterceptor)
        return ServiceGenerator.generate(
                BuildConfig.BASE_URL, MoviesApi::class.java, gson, null, interceptors
        )
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }
}