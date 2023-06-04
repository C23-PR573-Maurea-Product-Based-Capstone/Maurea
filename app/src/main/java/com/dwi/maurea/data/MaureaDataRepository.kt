package com.dwi.maurea.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwi.maurea.data.local.entitiy.ProfileEntity
import com.dwi.maurea.data.local.room.MaureaDao
import com.dwi.maurea.data.local.room.MaureaDatabase
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesPopularResponse
import com.dwi.maurea.data.remote.response.item.ItemSalesResponse
import com.dwi.maurea.data.remote.response.item.ItemSearchResponse
import com.dwi.maurea.network.ApiConfig.getService
import com.dwi.maurea.utils.Constanta.ACCESS_TOKEN
import com.dwi.maurea.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaureaDataRepository(application: Application) : MaureaDataSource {
    private val maureaDao: MaureaDao

    init {
        val maureaDatabase = MaureaDatabase.getInstance(application)
        maureaDao = maureaDatabase.maureaDao()
    }

    override suspend fun authRequest(email: String, password: String): Response<LoginResponse> {
        return getService().login(email, password)
    }

    override suspend fun auth(
        email: String,
        password: String
    ): LiveData<ApiResponse<LoginResponse>> {
        val authResult: MutableLiveData<ApiResponse<LoginResponse>> = MutableLiveData()
        authResult.value = ApiResponse.loading()
        try {
            val response = authRequest(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    authResult.value = ApiResponse.success(body)
                    SharedPrefUtils.saveString(
                        ACCESS_TOKEN,
                        "Bearer " + body.loginResult?.token.toString()
                    )
                    Log.d("MaureaRepositorySuccess", "auth: ${body.loginResult?.token.toString()}")
                }
            } else {
                authResult.value = ApiResponse.error(response.message())
                Log.d("MaureaRepositoryError", "auth: ${response.message()}")
            }
        } catch (e: Exception) {
            authResult.value = ApiResponse.error(e.message.toString())
            Log.d("MaureaRepositoryError", "auth: ${e.message.toString()}")
        }


        return authResult
    }

    override fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): LiveData<ApiResponse<RegisterResponse>> {
        val registerResult: MutableLiveData<ApiResponse<RegisterResponse>> = MutableLiveData()
        registerResult.value = ApiResponse.loading()

        getService().register(name, email, password, confirmPassword)
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            registerResult.value = ApiResponse.success(body)
                            Log.d("RegisterSuccess", "registerUser: ${body.message.toString()}")
                        }
                    } else {
                        registerResult.value = ApiResponse.error(response.message())
                        Log.d("RegisterError", "registerUser: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    registerResult.value = ApiResponse.error(t.message.toString())
                }
            })
        return registerResult

    }

    override suspend fun insertProfile(ProfileEntity: ProfileEntity) {
        maureaDao.insertProfile(ProfileEntity)
    }

    override fun getProfile(): LiveData<ProfileEntity> = maureaDao.getProfile()
    override fun getSalesItem(): LiveData<ApiResponse<ItemSalesResponse>> {
        val itemResult: MutableLiveData<ApiResponse<ItemSalesResponse>> = MutableLiveData()
        itemResult.value = ApiResponse.loading()

        getService().getItemSales(SharedPrefUtils.getString(ACCESS_TOKEN))
            .enqueue(object : Callback<ItemSalesResponse> {
                override fun onResponse(
                    call: Call<ItemSalesResponse>,
                    response: Response<ItemSalesResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            itemResult.value = ApiResponse.success(body)
                            Log.d("ItemSuccess", "getSalesItem: $body")
                        }
                    } else {
                        itemResult.value = ApiResponse.error(response.message())
                        Log.d("ItemError", "getSalesItem: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ItemSalesResponse>, t: Throwable) {
                    itemResult.value = ApiResponse.error(t.message.toString())
                    Log.d("ItemError", "getSalesItem: ${t.message.toString()}")
                }

            })
        return itemResult
    }

    override fun getSalesItemPopular(): LiveData<ApiResponse<ItemSalesPopularResponse>> {
        val itemPopular: MutableLiveData<ApiResponse<ItemSalesPopularResponse>> = MutableLiveData()
        itemPopular.value = ApiResponse.loading()

        getService().getItemSalesPopular(SharedPrefUtils.getString(ACCESS_TOKEN))
            .enqueue(object : Callback<ItemSalesPopularResponse> {
                override fun onResponse(
                    call: Call<ItemSalesPopularResponse>,
                    response: Response<ItemSalesPopularResponse>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            itemPopular.value = ApiResponse.success(body)
                            Log.d("ItemPopularSuccess", "getSalesItemPopular: $body")
                        }
                    } else {
                        itemPopular.value = ApiResponse.error(response.message())
                        Log.d("ItemPopularError", "getSalesItemPopular: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ItemSalesPopularResponse>, t: Throwable) {
                    itemPopular.value = ApiResponse.error(t.message.toString())
                    Log.d("ItemPopularError", "getSalesItemPopular: ${t.message.toString()}")
                }

            })

        return itemPopular
    }

    override fun getSearchFruits(query: String): LiveData<ApiResponse<ItemSalesResponse>> {
        val searchResult: MutableLiveData<ApiResponse<ItemSalesResponse>> = MutableLiveData()

        searchResult.value = ApiResponse.loading()

        getService().searchFruits(
            SharedPrefUtils.getString(ACCESS_TOKEN),
            query
        ).enqueue(object : Callback<ItemSalesResponse> {
            override fun onResponse(
                call: Call<ItemSalesResponse>,
                response: Response<ItemSalesResponse>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        searchResult.value = ApiResponse.success(body)
                        Log.d("SearchSuccess", "getSearchFruits: $body")
                    }
                } else {
                    searchResult.value = ApiResponse.error(response.message())
                    Log.d("SearchError", "getSearchFruits: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ItemSalesResponse>, t: Throwable) {
                searchResult.value = ApiResponse.error(t.message.toString())
                Log.d("SearchFailure", "getSearchFruits: ${t.message.toString()}")
            }
        })


        return searchResult
    }

}