package com.dwi.maurea.data.remote.response

//class ApiResponse<T> (val status: StatusResponse, val body: T?, val message: String?) {
//    companion object {
//        fun <T> loading(): ApiResponse<T> = ApiResponse(StatusResponse.LOADING, null, null)
//
//        fun <T> success(body: T): ApiResponse<T> = ApiResponse(StatusResponse.SUCCESS, body, null)
//
//        fun <T> error(msg: String): ApiResponse<T> = ApiResponse(StatusResponse.ERROR, null, msg)
//    }
//}

sealed class ApiResponse <out T> {
    object Loading: ApiResponse<Nothing>()
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val errorMessage: String): ApiResponse<Nothing>()
}