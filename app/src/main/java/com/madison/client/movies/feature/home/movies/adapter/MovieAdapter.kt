package com.madison.client.movies.feature.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.madison.client.movies.data.model.Movie
import com.madison.client.movies.databinding.ItemMovieBinding

class MovieAdapter(private val onMovieClickListener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var movies = ArrayList<Movie>()
    private var onClickListener: OnClickListener? = null

    fun injectData(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun injectDataWithLoadMore(movies: List<Movie>) {
        val curSize = itemCount
        this.movies.addAll(movies)
        this.notifyItemRangeInserted(curSize, itemCount)
    }

    fun clearData() {
        this.movies.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.getInstance(parent, onMovieClickListener)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bindData(movie)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder private constructor(
        private val binding: ItemMovieBinding,
        val onMovieClickListener: (Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var movie: Movie? = null

        companion object {
            fun getInstance(
                parent: ViewGroup,
                onMovieClickListener: (Movie) -> Unit
            ): MovieViewHolder {
                val binding = ItemMovieBinding.inflate( LayoutInflater.from(parent.context), parent, false)
                return MovieViewHolder(
                    binding,
                    onMovieClickListener
                )
            }
        }

        fun bindData(movie: Movie) {
            this.movie = movie
            binding.movie = movie
            binding.viewHolder = this@MovieViewHolder
            binding.executePendingBindings()
        }
    }

    fun setClickLister(clickListener: OnClickListener) {
        onClickListener = clickListener
    }

    interface OnClickListener {
        fun clickItem(movie: Movie)
    }
}
