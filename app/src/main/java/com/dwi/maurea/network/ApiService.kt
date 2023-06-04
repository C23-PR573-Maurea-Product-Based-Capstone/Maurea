package com.dwi.maurea.network

import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesPopularResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesResponse
import com.dwi.maurea.data.remote.response.item.ItemSearchResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginResponse>

    @GET("fruits")
    fun getItemSales(
        @Header("Authorization") authorization: String?,
    ): Call<ItemSalesResponse>

    @GET("fruits/popular")
    fun getItemSalesPopular(
        @Header("Authorization") authorization: String?,
    ): Call<ItemSalesPopularResponse>

    @GET("fruits")
    fun searchFruits(
        @Header("Authorization") authorization: String?,
        @Query("Nama") query: String,
    ): Call<ItemSalesResponse>
}
