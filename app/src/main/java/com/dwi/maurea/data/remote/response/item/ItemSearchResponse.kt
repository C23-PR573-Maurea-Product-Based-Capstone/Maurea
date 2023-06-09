package com.dwi.maurea.data.remote.response.item

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ItemSearchResponse(

	@field:SerializedName("search")
	val search: ArrayList<SearchItem>? = null
) : Parcelable

@Parcelize
data class SearchItem(

	@field:SerializedName("Gambar")
	val gambar: String? = null,

	@field:SerializedName("Nama")
	val nama: String? = null,

	@field:SerializedName("Harga")
	val harga: String? = null,

	@field:SerializedName("id")
	val id: String? = null
) : Parcelable
