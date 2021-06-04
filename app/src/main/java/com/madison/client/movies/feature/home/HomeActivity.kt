package com.madison.client.movies.feature.home

import android.os.Bundle
import com.madison.client.movies.R
import com.madison.client.movies.feature.base.BaseActivity
import com.madison.client.movies.feature.home.movies.MoviesFragment
import kotlinx.android.synthetic.main.activity_movies.*

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            navigator.addFragment(
                supportFragmentManager, MoviesFragment.newInstance(),
                R.id.contentFrame, false
            )
        }
    }
}