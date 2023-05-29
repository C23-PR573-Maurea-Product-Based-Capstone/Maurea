package com.dwi.maurea.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import com.dwi.maurea.R
import com.dwi.maurea.data.local.entitiy.ProfileEntity
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.data.remote.response.auth.LoginResult
import com.dwi.maurea.ui.MainActivity
import com.dwi.maurea.ui.login.LoginActivity
import com.dwi.maurea.utils.Constanta.ACCESS_TOKEN
import com.dwi.maurea.utils.Constanta.EMAIL
import com.dwi.maurea.utils.Constanta.PASSWORD
import com.dwi.maurea.utils.SharedPrefUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val email = SharedPrefUtils.getString(EMAIL)
        val password = SharedPrefUtils.getString(PASSWORD)
        Handler(mainLooper).postDelayed({
            if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                getAuthUser(email, password)
            }
        }, 3000)
    }

    private fun getAuthUser(email: String, password: String) {
        splashViewModel.auth(email, password)
            .observe(this@SplashActivity) { splash ->
                if (splash != null) {
                    when (splash.status) {
                        StatusResponse.LOADING -> {
                            // do nothing
                        }

                        StatusResponse.SUCCESS -> {
                            SharedPrefUtils.saveString(ACCESS_TOKEN, splash.body?.loginResult?.token)
                            insertToLocal(splash.body?.loginResult)

                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }

                        StatusResponse.ERROR -> {
                            Toast.makeText(
                                this,
                                splash.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
    }

    private fun insertToLocal(loginResult: LoginResult?) {
        val profileEntity = ProfileEntity(
            id = 0,
            name = loginResult?.name,
            email = loginResult?.email,
        )
        splashViewModel.insertUsers(profileEntity)
    }
}