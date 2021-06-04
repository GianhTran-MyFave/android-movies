package com.madison.client.movies.feature.details.moviedetails

import androidx.lifecycle.MutableLiveData
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.repository.MoviesRepository
import com.madison.client.movies.data.repository.remote.api.error.RetrofitException
import com.madison.client.movies.feature.base.BaseViewModel
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(private var moviesRepository: MoviesRepository) :
    BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    var movie = MutableLiveData<Movie>()
    var loadingError = MutableLiveData<RetrofitException>()

    fun getMovieDetails(id: Int) {
        compositeDisposable.add(moviesRepository.getMovieDetails(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe {
                isLoading.value = true
            }.doFinally {
                isLoading.value = false
            }.subscribe({
                movie.value = it
            }, {
                if (it is RetrofitException) loadingError.value = it
            })
        )
    }
}