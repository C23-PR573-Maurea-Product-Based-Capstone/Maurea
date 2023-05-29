package com.dwi.maurea.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dwi.maurea.data.MaureaDataRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val mMaureaDataRepository = MaureaDataRepository(application)

    fun getProfile() = mMaureaDataRepository.getProfile()

    fun getPopularItems() = mMaureaDataRepository.getSalesItemPopular()
}