package com.madison.client.movies.data.repository.remote.api.error

import com.madison.client.movies.data.repository.remote.api.response.ServerErrorResponse
import retrofit2.Response
import java.io.IOException

class RetrofitException(
    private val kind: Kind,
    private val serverErrorResponse: ServerErrorResponse? = null,
    private val response: Response<*>? = null,
    private val exception: Throwable? = null,
    private val errorCode: Int? = null
) : RuntimeException() {

    companion object {
        fun httpError(response: Response<*>): RetrofitException {
            return RetrofitException(kind = Kind.HTTP, response = response)
        }

        fun serverError(
            serverErrorResponse: ServerErrorResponse,
            errorCode: Int
        ): RetrofitException {
            return RetrofitException(
                kind = Kind.SERVER,
                serverErrorResponse = serverErrorResponse,
                errorCode = errorCode
            )
        }

        fun networkError(throwable: Throwable): RetrofitException {
            return RetrofitException(
                kind = Kind.NETWORK,
                exception = throwable
            )
        }

        fun unexpectedError(throwable: Throwable): RetrofitException {
            return RetrofitException(
                kind = Kind.NETWORK,
                exception = throwable
            )
        }
    }

    private fun getServerMessage(): String? {
        return if (kind == Kind.SERVER && serverErrorResponse != null) {
            serverErrorResponse.message
        } else ""
    }

    private fun getHTTPMessage(): String? {
        return if (kind == Kind.HTTP) {
            "Error HTTP"
        } else ""
    }

    private fun getNetworkMessage(): String? {
        return if (kind == Kind.NETWORK) {
            "Error Network"
        } else ""
    }

    private fun getUnExpectedMessage(): String? {
        return if (kind == Kind.UNEXPECTED) {
            "Error UnExpected"
        } else ""
    }

    fun getErrorMessage(): String? {
        return when (kind) {
            Kind.SERVER -> getServerMessage()
            Kind.HTTP -> getHTTPMessage()
            Kind.NETWORK -> getNetworkMessage()
            else -> getUnExpectedMessage()
        }
    }

    fun getErrorCode(): Int? {
        return errorCode
    }

    fun isNetworkError(): Boolean {
        return kind == Kind.NETWORK
    }

    fun isServerError(): Boolean {
        return kind == Kind.SERVER
    }

    enum class Kind {
        /** An [IOException] occurred while communicating to the server.  */
        NETWORK,
        /** A non-200 HTTP status code was received from the server.  */
        HTTP,
        SERVER,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}