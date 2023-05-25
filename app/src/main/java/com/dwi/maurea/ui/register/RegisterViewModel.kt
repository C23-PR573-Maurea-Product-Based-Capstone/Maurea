package com.dwi.maurea.ui.register

import androidx.lifecycle.ViewModel
import com.dwi.maurea.data.MaureaDataRepository

class RegisterViewModel: ViewModel() {
    private val mMaureaDataRepository = MaureaDataRepository()

    fun registerUser(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) = mMaureaDataRepository.registerUser(name, email, password, confirmPassword)
}