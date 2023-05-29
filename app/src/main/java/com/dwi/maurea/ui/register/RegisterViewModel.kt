package com.dwi.maurea.ui.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.dwi.maurea.data.MaureaDataRepository

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val mMaureaDataRepository = MaureaDataRepository(application)

    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) = mMaureaDataRepository.registerUser(name, email, password, confirmPassword)
}