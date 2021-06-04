package com.madison.client.movies.feature.details

import com.madison.client.movies.di.scope.FragmentScope
import com.madison.client.movies.feature.details.moviedetails.MovieDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MovieDetailsModule {

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindMovieDetailsFragment(): MovieDetailsFragment
}