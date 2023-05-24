package com.dwi.maurea.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwi.maurea.data.MaureaDataRepository
import com.dwi.maurea.data.remote.response.ApiResponse
import com.dwi.maurea.data.remote.response.auth.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {
    private val mMaureaDataRepository = MaureaDataRepository()

    fun auth(email: String, password: String): LiveData<ApiResponse<LoginResponse>> {
        val authResult = MutableLiveData<ApiResponse<LoginResponse>>()
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authResult.postValue(mMaureaDataRepository.auth(email, password).value)
            }
        }
        return authResult
    }
}