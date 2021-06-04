package com.madison.client.movies.data.repository.remote.api.service

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.madison.client.movies.data.repository.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {

    companion object {

        private const val CONNECTION_TIMEOUT_IN_SECOND = 15L

        fun <T> generate(
            baseUrl: String,
            serviceClass: Class<T>,
            gson: Gson,
            authenticator: Authenticator?,
            interceptors: Array<Interceptor>
        ): T {
            val okHttpClientBuilder = OkHttpClient.Builder()

            for (interceptor in interceptors) {
                okHttpClientBuilder.addInterceptor(interceptor)
            }

            okHttpClientBuilder.connectTimeout(CONNECTION_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
            okHttpClientBuilder.readTimeout(CONNECTION_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

            if (authenticator != null) {
                okHttpClientBuilder.authenticator(authenticator)
            }

            val builder = Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))

            val retrofit = builder.client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

            return retrofit.create(serviceClass)
        }
    }
}