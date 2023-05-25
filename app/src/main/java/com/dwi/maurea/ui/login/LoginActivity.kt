package com.dwi.maurea.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.databinding.ActivityLoginBinding
import com.dwi.maurea.ui.MainActivity
import com.dwi.maurea.ui.register.RegisterActivity
import com.dwi.maurea.utils.TextUtils.setBoldClickableText

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

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

        binding.etEmail.setText("dwe@gmail.com")
        binding.etPassword.setText("password123")

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            if (isFieldValid(email, password)) {
                viewModel.auth(email.toString(), password.toString())
                    .observe(this@LoginActivity) { login ->
                        if (login != null) {
                            when (login.status) {
                                StatusResponse.LOADING -> {
                                    isLoading(true)
                                }

                                StatusResponse.SUCCESS -> {
                                    isLoading(false)
                                    Toast.makeText(
                                        this,
                                        "Login berhasil",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }

                                StatusResponse.ERROR -> {
                                    isLoading(false)
                                    Toast.makeText(
                                        this,
                                        login.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun isFieldValid(etEmail: Editable?, etPassword: Editable?): Boolean {

        when {
            etEmail.isNullOrEmpty() -> {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.isNullOrEmpty() -> {
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.length < 8 -> {
                binding.etPassword.error = "Password minimal 8 karakter"
                return false
            }

            else -> return true
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}