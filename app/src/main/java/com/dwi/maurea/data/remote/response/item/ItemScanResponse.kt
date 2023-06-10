package com.dwi.maurea.data.remote.response.item

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ItemScanResponse(

	@field:SerializedName("scannableitem")
	val scannableitem: List<ItemScan>? = null
) : Parcelable

@Parcelize
data class ItemScan(

	@field:SerializedName("Gambar")
	val gambar: String? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
