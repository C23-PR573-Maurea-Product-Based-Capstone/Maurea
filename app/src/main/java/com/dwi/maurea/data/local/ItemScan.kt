package com.dwi.maurea.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemScan(
    val id: String = "",
    val gambar: String = "",
    val nama: String = "",
) : Parcelable
