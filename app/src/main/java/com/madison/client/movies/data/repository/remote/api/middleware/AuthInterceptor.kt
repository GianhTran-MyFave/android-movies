package com.madison.client.movies.data.repository.remote.api.middleware

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : ApiInterceptor() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder().method(original.method(), original.body())
        return chain.proceed(builder.build())
    }
}