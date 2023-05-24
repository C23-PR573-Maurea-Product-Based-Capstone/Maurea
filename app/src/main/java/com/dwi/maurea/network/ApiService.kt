package com.dwi.maurea.network

import androidx.annotation.RawRes
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("users")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
    ): Call<RegisterResponse>

//    @FormUrlEncoded
//    @POST("login")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("password") password: String,
//    ): Response<LoginResponse>

    @POST("login")
    suspend fun login(
        @Body loginRequest: RequestBody
    ): Response<LoginResponse>
}
