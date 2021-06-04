package com.madison.client.movies.data.repository.remote.api.middleware

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.madison.client.movies.data.repository.remote.api.error.RetrofitException
import com.madison.client.movies.data.repository.remote.api.response.ServerErrorResponse
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    private val original by lazy {
        RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    companion object {
        private val TAG: String = RxErrorHandlingCallAdapterFactory::class.java.simpleName
        fun create(): CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }

    override fun get(
        returnType: Type, annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val wrapped = original.get(returnType, annotations, retrofit) as CallAdapter<*, *>
        return RxCallAdapterWrapper(wrapped)
    }

    private inner class RxCallAdapterWrapper<R>(
        val wrappedCallAdapter: CallAdapter<R, *>
    ) : CallAdapter<R, Single<R>> {

        override fun responseType(): Type = wrappedCallAdapter.responseType()


        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Single<R>? {
            return (wrappedCallAdapter.adapt(
                call
            ) as Single<R>).onErrorResumeNext { throwable: Throwable ->
                Single.error(asRetrofitException(throwable))
            }
        }

        private fun asRetrofitException(throwable: Throwable): RetrofitException {
            if (throwable is HttpException) {
                val response = throwable.response()
                val errorBody = response.errorBody()
                val errorCode = response.raw().code()
                if (errorBody != null) {
                    try {
                        val errorResponse = errorBody.string()
                        val serverErrorResponse = deserializeServerError(errorResponse)
                        return if (serverErrorResponse != null) {
                            RetrofitException.serverError(serverErrorResponse, errorCode)
                        } else RetrofitException.httpError(response)
                    } catch (e: IOException) {
                        Log.e(TAG, e.message ?: "")
                    }
                }
            }

            if (throwable is IOException) {
                return RetrofitException.networkError(throwable)
            }
            return RetrofitException.unexpectedError(throwable)
        }

        private fun deserializeServerError(errorString: String): ServerErrorResponse? {
            val gson = GsonBuilder().create()
            return try {
                gson.fromJson(errorString, ServerErrorResponse::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }
        }
    }
}