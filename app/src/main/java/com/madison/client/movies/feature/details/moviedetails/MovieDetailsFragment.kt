package com.madison.client.movies.feature.details.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.databinding.FragmentMovieDetailBinding
import com.madison.client.movies.extention.helper.viewextension.safeClick
import com.madison.client.movies.feature.base.BaseFragment
import com.madison.client.movies.feature.bookmovie.BookMovieActivity

class MovieDetailsFragment : BaseFragment() {
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var binding: FragmentMovieDetailBinding

    companion object {
        const val MOVIE_EXTRA_KEY = "MOVIE_EXTRA_KEY"

        fun newInstance(movie: Movie?): MovieDetailsFragment {
            val bundle = Bundle().apply {
                putParcelable(MOVIE_EXTRA_KEY, movie)
            }
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        movieDetailsViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MovieDetailsViewModel::class.java)
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding.viewModel = movieDetailsViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun initView() {
        arguments?.let {
            if (it.containsKey(MOVIE_EXTRA_KEY)) {
                it.getParcelable<Movie>(MOVIE_EXTRA_KEY)?.let { movie ->
                    movie.id?.let { id ->
                        movieDetailsViewModel.getMovieDetails(id)
                    }
                }
            }
        }
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBookMovie.safeClick(View.OnClickListener {
            navigator.startActivity(requireActivity(), BookMovieActivity::class.java)
        })
    }

    override fun onBackPressedCallback() {
        super.onBackPressedCallback()
        requireActivity().finish()
    }
}