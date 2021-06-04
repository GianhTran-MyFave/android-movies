package com.madison.client.movies.feature.base

import androidx.lifecycle.ViewModel
import com.madison.client.movies.extention.helper.scheduler.AppSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val schedulerProvider = AppSchedulerProvider()

    val compositeDisposable = CompositeDisposable()

    /**
     * Remove all disposables
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}