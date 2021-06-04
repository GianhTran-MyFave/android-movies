package com.madison.client.movies.extention.helper.scheduler

import io.reactivex.Scheduler


interface SchedulerProvider {
    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}