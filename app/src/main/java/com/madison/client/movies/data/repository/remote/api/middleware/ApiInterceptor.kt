package com.madison.client.movies.data.repository.remote.api.middleware

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


open class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(buildRequest(chain))
    }

    open fun buildRequest(chain: Interceptor.Chain): Request {
        return chain.request()
    }
}