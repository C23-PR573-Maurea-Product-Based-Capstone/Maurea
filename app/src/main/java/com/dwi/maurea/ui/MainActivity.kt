package com.dwi.maurea.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dwi.maurea.R
import com.dwi.maurea.utils.Constanta
import com.dwi.maurea.utils.SharedPrefUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "token : ${SharedPrefUtils.getString(Constanta.ACCESS_TOKEN)}")
    }
}