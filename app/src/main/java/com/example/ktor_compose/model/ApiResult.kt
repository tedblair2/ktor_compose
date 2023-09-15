package com.example.ktor_compose.model

sealed class ApiResult<T>(val data:T?=null, val error:String?=null){
    class Success<T>(quotes: T):ApiResult<T>(data = quotes)
    class Error<T>(error: String):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}
