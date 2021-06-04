package com.madison.client.movies.feature.home

import com.madison.client.movies.di.scope.FragmentScope
import com.madison.client.movies.feature.home.movies.MoviesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun bindMoviesFragment(): MoviesFragment
}