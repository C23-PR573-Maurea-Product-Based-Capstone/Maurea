package com.dwi.maurea.data.remote.response.item

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ItemSalesResponse(
	@field:SerializedName("item")
	val item: List<ItemItem?>? = null
) : Parcelable

@Parcelize
data class ItemItem(

	@field:SerializedName("Gambar")
	val gambar: String? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Harga")
	val harga: String? = null
) : Parcelable
