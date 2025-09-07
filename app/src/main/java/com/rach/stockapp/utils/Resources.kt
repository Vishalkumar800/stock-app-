package com.rach.stockapp.utils

sealed class Resources<T>(
    val data:T? = null,
    val message: String?= null
) {
    class Success<T>(data:T? = null): Resources<T>(data = data)
    class Error<T>(errorMessage: String , data:T? = null) : Resources<T>(data = data, message = errorMessage)
    class Loading<T>: Resources<T>()
}