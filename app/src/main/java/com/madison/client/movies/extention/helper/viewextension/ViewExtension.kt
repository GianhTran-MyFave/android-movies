package com.madison.client.movies.extention.helper.viewextension

import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madison.client.movies.R
import com.madison.client.movies.data.model.Genres
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.extention.helper.utils.IMAGE_BASE_URL
import com.madison.client.movies.feature.home.movies.adapter.MovieAdapter

fun Button.safeClick(listener: View.OnClickListener, blockInMillis: Long = 500) {
    var lastClickTime: Long = 0
    this.setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime < blockInMillis) return@setOnClickListener
        lastClickTime = SystemClock.elapsedRealtime()
        listener.onClick(this)
    }
}

fun View.handleGoneVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("duration")
fun TextView.setDurationTime(duration: Int?) {
    duration?.let {
        val text = context.getString(R.string.duration_time, duration)
        setText(text)
    }
}

@BindingAdapter("genres")
fun TextView.setGenres(genres: List<Genres>?) {
    val text = genres?.joinToString(transform = { it.name ?: "" }, separator = "#")
    setText(text)
}

@BindingAdapter("voteCount")
fun TextView.setVoteCount(voteCount: Int?) {
    val text = voteCount.toString() + " votes"
    setText(text)
}

@BindingAdapter("voteAvg")
fun TextView.setVoteAvg(voteAvg: Float?) {
    val text = voteAvg.toString() + "/10"
    setText(text)
}

@BindingAdapter("movieImage")
fun ImageView.loadImage(imageUrl: String?) {
    val fullImageUrl = IMAGE_BASE_URL + imageUrl
    Glide.with(this.context)
        .load(fullImageUrl)
        .placeholder(R.drawable.image_placeholder)
        .into(this)
}

@BindingAdapter("movieList", "pageNumber")
fun RecyclerView.submitMovieList(movies: List<Movie>?, pageNumber: Int = 1) {
    val adapter = this.adapter as MovieAdapter
    movies?.let {
        if (pageNumber == 1) {
            adapter.injectData(movies)
        } else {
            adapter.injectDataWithLoadMore(movies)
        }
    }
}

@BindingAdapter("firstPageloading", "pageNumber")
fun ProgressBar.firstPageLoadingVisibility(
    loading: Boolean?,
    pageNumber: Int = 1
) {
    this.handleGoneVisibility(loading ?: false && pageNumber == 1)
}

@BindingAdapter("paginationLoading", "pageNumber")
fun ProgressBar.paginationLoadingVisibility(
    loading: Boolean?,
    pageNumber: Int = 1
) {
    this.handleGoneVisibility(loading ?: false && pageNumber > 1)
}


