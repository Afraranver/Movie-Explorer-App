package com.example.movieexplorerapp.common

import okhttp3.ResponseBody

sealed class NetworkResult<out T> {

    data class Success<out T>(val value: T) : NetworkResult<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?,
        val errorMessage: String?
    ) : NetworkResult<Nothing>()

    data object Loading : NetworkResult<Nothing>()

}