package com.dwi.maurea.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.ui.MainActivity
import com.dwi.maurea.utils.Constanta.EMAIL
import com.dwi.maurea.utils.Constanta.PASSWORD
import com.dwi.maurea.utils.SharedPrefUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val email = SharedPrefUtils.getString(EMAIL)
        val password = SharedPrefUtils.getString(PASSWORD)
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, 3000)
    }
}