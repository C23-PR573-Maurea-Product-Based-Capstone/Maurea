package com.dwi.maurea.data

import androidx.lifecycle.LiveData
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import retrofit2.Response

interface MaureaDataSource {
    suspend fun authRequest(email: String, password: String): Response<LoginResponse>

    suspend fun auth(email: String, password: String): LiveData<ApiResponse<LoginResponse>>

    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): LiveData<ApiResponse<RegisterResponse>>
}