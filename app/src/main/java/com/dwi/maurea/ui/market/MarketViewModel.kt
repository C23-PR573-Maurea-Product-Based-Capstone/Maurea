package com.dwi.maurea.ui.market

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dwi.maurea.data.MaureaDataRepository

class MarketViewModel(application: Application): AndroidViewModel(application) {
    private val mMaureaDataRepository = MaureaDataRepository(application)

    fun getAllItemSales() = mMaureaDataRepository.getSalesItem()

    fun getSearchItems(query: String) = mMaureaDataRepository.getSearchFruits(query)
}