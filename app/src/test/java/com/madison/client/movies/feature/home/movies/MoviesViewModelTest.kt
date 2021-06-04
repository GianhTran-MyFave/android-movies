package com.madison.client.movies.feature.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.madison.client.movies.RxImmediateSchedulerRule
import com.madison.client.movies.data.model.Category
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.data.model.MovieResponse
import com.madison.client.movies.data.repository.MoviesRepository
import com.madison.client.movies.data.repository.remote.MoviesRemoteDataSource
import com.madison.client.movies.data.repository.remote.api.MoviesApi
import io.reactivex.Single
import org.junit.Before
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.text.SimpleDateFormat

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {
    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Mock
    lateinit var moviesApi: MoviesApi

    @Mock
    lateinit var observer: Observer<List<Movie>>

    lateinit var moviesViewModel: MoviesViewModel

    lateinit var moviesRepository: MoviesRepository

    lateinit var moviesRemoteDataSource: MoviesRemoteDataSource

    @Before
    fun setUp() {
        // initialize the MoviesViewModel with a mocked MoviesRepository
        moviesRemoteDataSource = MoviesRemoteDataSource(moviesApi)
        moviesRepository = MoviesRepository(moviesRemoteDataSource)
        moviesViewModel = MoviesViewModel(moviesRepository)
    }

    @Test
    fun shouldReturnCorrectDataWhenFetchMovieSuccess() {
        // mock data
        val page = 1
        val sortBy = Category.RELEASE_DATE.category
        val movies = arrayListOf<Movie>()
        for (i in 0 until 10) {
            movies.add(Movie.newInstance())
        }
        val movieResponse = MovieResponse(1, 10, 10, movies)

        // make the moviesRepository return mock data
        Mockito.`when`(moviesApi.getMovies(page, sortBy))
            .thenReturn(Single.just(movieResponse))

        // observe on the MutableLiveData with an observer
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.getMovies(page, sortBy)

        // assert that the data is correct
        assert(moviesViewModel.movies.value?.count() == 10)
    }

    @Test
    fun shouldReturnCorrectDataWhenOrderByReleaseDate() {
        //mock data
        val page = 1
        val sortBy = Category.RELEASE_DATE.category
        val movies = arrayListOf<Movie>()
        for (i in 0 until 10) {
            val releaseDate = "2021-10-${i + 1}"
            movies.add(Movie.newInstance(releaseDate = releaseDate))
        }
        movies.reverse()
        val movieResponse = MovieResponse(1, 10, 10, movies)

        // make the moviesRepository return mock data
        Mockito.`when`(moviesApi.getMovies(page, sortBy))
            .thenReturn(Single.just(movieResponse))

        // observe on the MutableLiveData with an observer
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.getMovies(page, sortBy)

        var isMovieSortedCorrect = true
        val response = moviesViewModel.movies.value
        for (i in 0 until 9) {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val curDate = sdf.parse(response?.get(i)?.releaseDate!!)
            val nextDate = sdf.parse(response.get(i + 1).releaseDate!!)
            if (curDate < nextDate) {
                isMovieSortedCorrect = false
            }
        }
        assert(isMovieSortedCorrect)
    }

    @Test
    fun shouldReturnCorrectDataWhenOrderByRating() {
        //mock data
        val page = 1
        val sortBy = Category.RATING.category
        val movies = arrayListOf<Movie>()
        for (i in 0 until 10) {
            movies.add(Movie.newInstance(voteAverage = i.toFloat()))
        }
        movies.reverse()
        val movieResponse = MovieResponse(1, 10, 10, movies)

        // make the moviesRepository return mock data
        Mockito.`when`(moviesApi.getMovies(page, sortBy))
            .thenReturn(Single.just(movieResponse))

        // observe on the MutableLiveData with an observer
        moviesViewModel.movies.observeForever(observer)
        moviesViewModel.getMovies(page, sortBy)

        var isMovieSortedCorrect = true
        val response = moviesViewModel.movies.value
        for (i in 0 until 9) {
            if (response?.get(i)?.voteAverage!! < response.get(i + 1).voteAverage!!) {
                isMovieSortedCorrect = false
            }
        }
        assert(isMovieSortedCorrect)
    }
}