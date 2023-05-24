package com.dwi.maurea.network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object BodyRequest {

    fun loginBodyRequest(email: String?, password: String): RequestBody {
        val mediaType: MediaType? = "application/json".toMediaTypeOrNull()
        val rawBody = """
            {
                "email": "$email",
                "password": "$password"
            }
        """.trimIndent()
        return rawBody.toRequestBody(mediaType)
    }
}