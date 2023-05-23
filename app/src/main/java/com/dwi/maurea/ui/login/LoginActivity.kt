package com.dwi.maurea.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.databinding.ActivityLoginBinding
import com.dwi.maurea.ui.register.RegisterActivity
import com.dwi.maurea.utils.TextUtils.setBoldClickableText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBoldClickableText(
            binding.tvRegister,
            R.string.belum_punya_akun,
            R.string.daftar_disini
        ) {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}