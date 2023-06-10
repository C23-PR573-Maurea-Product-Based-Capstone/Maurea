package com.dwi.maurea.data

import androidx.lifecycle.LiveData
import com.dwi.maurea.data.local.entitiy.ProfileEntity
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesPopularResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesResponse
import com.dwi.maurea.data.remote.response.item.ItemScanResponse
import com.dwi.maurea.data.remote.response.item.ItemSearchResponse
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

    suspend fun insertProfile(ProfileEntity: ProfileEntity)

    fun getProfile(): LiveData<ProfileEntity>

    fun getSalesItem(): LiveData<ApiResponse<ItemSalesResponse>>

    fun getSalesItemPopular(): LiveData<ApiResponse<ItemSalesPopularResponse>>

    fun getSearchFruits(query: String): LiveData<ApiResponse<ItemSalesResponse>>

    fun getFruitsScan(): LiveData<ApiResponse<ItemScanResponse>>
}