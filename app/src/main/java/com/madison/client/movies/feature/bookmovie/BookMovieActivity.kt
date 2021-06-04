package com.madison.client.movies.feature.bookmovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import com.madison.client.movies.R
import com.madison.client.movies.extention.helper.utils.BOOK_MOVIE_URL
import com.madison.client.movies.feature.base.BaseActivity
import kotlinx.android.synthetic.main.activity_book_movie.*

class BookMovieActivity : BaseActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_movie)
        setSupportActionBar(toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(BOOK_MOVIE_URL)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}