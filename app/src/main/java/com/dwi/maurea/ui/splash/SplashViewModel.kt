package com.dwi.maurea.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dwi.maurea.data.MaureaDataRepository
import com.dwi.maurea.data.local.entitiy.ProfileEntity
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(application: Application) : AndroidViewModel(application) {
    private val mMaureaDataRepository = MaureaDataRepository(application)

    fun auth(email: String, password: String): LiveData<ApiResponse<LoginResponse>> {
        val authResult = MutableLiveData<ApiResponse<LoginResponse>>()
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authResult.postValue(
                    mMaureaDataRepository.auth(email, password).value
                )
            }
        }
        return authResult
    }

    fun insertUsers(profileEntity: ProfileEntity) {
        viewModelScope.launch {
            mMaureaDataRepository.insertProfile(profileEntity)
        }
    }
}