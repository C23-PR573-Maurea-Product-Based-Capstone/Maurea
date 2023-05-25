package com.dwi.maurea.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dwi.maurea.R
import com.dwi.maurea.ui.MainActivity
import com.dwi.maurea.ui.login.LoginActivity
import com.dwi.maurea.utils.Constanta.ACCESS_TOKEN
import com.dwi.maurea.utils.SharedPrefUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            if (SharedPrefUtils.getString(ACCESS_TOKEN).isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 3000)
    }
}