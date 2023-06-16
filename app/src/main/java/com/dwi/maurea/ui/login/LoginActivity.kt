package com.dwi.maurea.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.data.local.entitiy.ProfileEntity
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.data.remote.response.auth.LoginResult
import com.dwi.maurea.databinding.ActivityLoginBinding
import com.dwi.maurea.ui.MainActivity
import com.dwi.maurea.ui.register.RegisterActivity
import com.dwi.maurea.utils.Constanta.EMAIL
import com.dwi.maurea.utils.Constanta.PASSWORD
import com.dwi.maurea.utils.SharedPrefUtils
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

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            if (isFieldValid(email, password)) {
                isLoading(true)
                viewModel.auth(email.toString(), password.toString())
                    .observe(this@LoginActivity) { login ->
                        if (login != null) {
                            when (login.status) {
                                StatusResponse.LOADING -> {
                                    isLoading(true)
                                }

                                StatusResponse.SUCCESS -> {
                                    isLoading(false)
                                    SharedPrefUtils.saveString(
                                        EMAIL, email.toString()
                                    )
                                    SharedPrefUtils.saveString(PASSWORD, password.toString())
                                    insertToLocal(login.body?.loginResult)
                                    Toast.makeText(
                                        this,
                                        getString(R.string.login_berhasil),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }

                                StatusResponse.ERROR -> {
                                    isLoading(false)
                                    Toast.makeText(
                                        this,
                                        getString(R.string.login_error),
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
                Toast.makeText(this, getString(R.string.email_tidak_boleh_kosong), Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.isNullOrEmpty() -> {
                Toast.makeText(this, getString(R.string.password_tidak_boleh_kosong), Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.length < 8 -> {
                Toast.makeText(this, getString(R.string.password_minimal_8_karakter), Toast.LENGTH_SHORT).show()
                return false
            }

            else -> return true
        }
    }

    private fun isLoading(state: Boolean) {
        binding.progressBar.visibility = View.GONE
        if (state) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }

    private fun insertToLocal(loginResult: LoginResult?) {
        val profileEntity = ProfileEntity(
            id = 0,
            name = loginResult?.name,
            email = loginResult?.email,
        )
        viewModel.insertUsers(profileEntity)
    }

}