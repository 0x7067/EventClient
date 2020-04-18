package com.pedro.eventsapp.data.network

import retrofit2.HttpException

interface ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T>
    fun <T : Any> handleException(e: Exception): Resource<T>
}

object ResponseHandlerImpl : ResponseHandler {
    override fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    override fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            401 -> "Unauthorized"
            404 -> "Not found"
            else -> "Something went wrong"
        }
    }
}