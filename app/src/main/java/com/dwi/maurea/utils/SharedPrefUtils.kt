package com.dwi.maurea.utils

import android.content.Context
import android.content.SharedPreferences
import com.dwi.maurea.BaseApp
import com.dwi.maurea.BuildConfig

object SharedPrefUtils {
    private fun pref(): SharedPreferences {
        return BaseApp.context!!.getSharedPreferences(
            // get application id
            BuildConfig.APPLICATION_ID,
            Context.MODE_PRIVATE
        )
    }

    private fun edit(): SharedPreferences.Editor = pref().edit()

    fun saveString(key: String, value: String?) = edit().putString(key, value).apply()

    fun getString(key: String): String? = pref().getString(key, null)

    fun clear() = edit().clear().apply()

    fun remove(key: String) = edit().remove(key).apply()
}