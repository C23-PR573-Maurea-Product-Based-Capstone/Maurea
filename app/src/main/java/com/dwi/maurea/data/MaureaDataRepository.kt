package com.dwi.maurea.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import com.dwi.maurea.data.remote.response.auth.RegisterResponse
import com.dwi.maurea.network.ApiConfig.getService
import com.dwi.maurea.network.BodyRequest.loginBodyRequest
import com.dwi.maurea.utils.Constanta
import com.dwi.maurea.utils.SharedPrefUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaureaDataRepository : MaureaDataSource {
    override suspend fun authRequest(email: String, password: String): Response<LoginResponse> {
        return getService().login(email, password)
    }

    override suspend fun auth(
        email: String,
        password: String
    ): LiveData<ApiResponse<LoginResponse>> {
        val authResult: MutableLiveData<ApiResponse<LoginResponse>> = MutableLiveData()
        try {
            val response = authRequest(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    authResult.value = ApiResponse.success(body)
                    SharedPrefUtils.saveString(
                        Constanta.ACCESS_TOKEN,
                        "Bearer " + body.loginResult?.token.toString()
                    )
                    Log.d("MaureaRepositorySuccess", "auth: ${body.loginResult?.token.toString()}")
                }
            } else {
                authResult.value = ApiResponse.error(response.message())
                Log.d("MaureaRepositoryError", "auth: ${response.message()}")
            }
        } catch (e: Exception) {
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

}