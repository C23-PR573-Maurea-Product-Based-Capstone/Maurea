package com.dwi.maurea.ui.scan.fruits

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dwi.maurea.data.MaureaDataRepository

class FruitsChoiceViewModel(application: Application) : AndroidViewModel(application) {
    private val mMaureaDataRepository = MaureaDataRepository(application)

    fun getFruits() = mMaureaDataRepository.getFruitsScan()
}