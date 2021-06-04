package com.madison.client.movies.feature.home.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.madison.client.movies.data.model.Category
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.repository.MoviesRepository
import com.madison.client.movies.data.repository.remote.api.error.RetrofitException
import com.madison.client.movies.feature.base.BaseViewModel
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private var moviesRepository: MoviesRepository) :
    BaseViewModel() {

    companion object {
        private const val ITEM_NUMBER_PER_PAGE = 20
    }

    val isLoading = MutableLiveData<Boolean>()
    val pageNumber: LiveData<Int>
        get() = _pageNumber

    var movies = MutableLiveData<List<Movie>>()
    var loadingError = MutableLiveData<RetrofitException>()
    var sortBy: String = Category.RELEASE_DATE.category

    private var isQueryExhausted: Boolean = false
    private var isExecutingQuery: Boolean = false

    private val _pageNumber = MutableLiveData<Int>()

    init {
        _pageNumber.value = 1
    }

    /**
     * fetch movies from remote at page number 1
     */
    fun fetchMoviesFromFirstPage() {
        resetPageNumber()
        if (!isExecutingQuery) {
            isQueryExhausted = false
            isExecutingQuery = true
            getMovies(_pageNumber.value ?: 1, sortBy)
        }
    }

    /**
     * fetch movies from remote at _pageNumber
     */
    fun fetchMoviesOfNextPage() {
        if (!isQueryExhausted && !isExecutingQuery) {
            _pageNumber.value = _pageNumber.value?.plus(1)
            getMovies(_pageNumber.value ?: 1, sortBy)
        }
    }

    private fun resetPageNumber() {
        _pageNumber.value = 1
    }

    fun getMovies(page: Int, sortBy: String) {
        compositeDisposable.add(moviesRepository.getMovies(page, sortBy)
            .subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui()).doOnSubscribe {
                isLoading.value = true
            }.doFinally {
                isLoading.value = false
            }.subscribe({
                movies.value = it.results
                isExecutingQuery = false
                isQueryExhausted = it.results?.size ?: 0 < ITEM_NUMBER_PER_PAGE
            }, {
                if (it is RetrofitException) loadingError.value = it
            })
        )
    }
}