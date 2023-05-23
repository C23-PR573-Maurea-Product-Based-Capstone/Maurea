package com.dwi.maurea.ui.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.databinding.ActivityRegisterBinding
import com.dwi.maurea.utils.TextUtils.setBoldClickableText

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBoldClickableText(
            binding.tvLogin,
            R.string.sudah_punya_akun_masuk,
            R.string.masuk,
        ) {
            finish()
        }
    }
}