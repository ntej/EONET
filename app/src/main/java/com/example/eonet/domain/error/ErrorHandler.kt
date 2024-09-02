package com.example.eonet.domain.error

import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object ErrorHandler {
    fun handleError(throwable: Throwable): String {
        return when (throwable) {
            is IOException -> "Network error. Please check your internet connection."
            is HttpException -> {
                when (throwable.code()) {
                    400 -> "Bad request. Please try again."
                    401 -> "Unauthorized access. Please log in again."
                    403 -> "Forbidden. You do not have permission to perform this action."
                    404 -> "Resource not found."
                    500 -> "Server error. Please try again later."
                    else -> "Unexpected server error. Code: ${throwable.code()}"
                }
            }
            is SocketTimeoutException, is TimeoutException -> "Request timed out. Please try again."
            is UnknownHostException -> "Unable to reach the server. Please check your internet connection."
            else -> "An unexpected error occurred: ${throwable.localizedMessage ?: "Unknown error."}"
        }
    }
}