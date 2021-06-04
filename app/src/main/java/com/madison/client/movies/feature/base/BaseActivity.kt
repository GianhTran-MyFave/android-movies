package com.madison.client.movies.feature.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.madison.client.movies.extention.helper.navigation.Navigator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

