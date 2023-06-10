package com.dwi.maurea.ui.register

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dwi.maurea.R
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.databinding.ActivityRegisterBinding
import com.dwi.maurea.utils.TextUtils.setBoldClickableText

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

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

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text
            val email = binding.etEmail.text
            val password = binding.etPassword.text
            val confirmPassword = binding.etConfirmPassword.text
            if (isFieldValid(email, name, password, confirmPassword)) {
                viewModel.registerUser(
                    name.toString(),
                    email.toString(),
                    password.toString(),
                    confirmPassword.toString(),
                ).observe(this@RegisterActivity) { register ->
                    if (register != null) {
                        when (register.status) {
                            StatusResponse.LOADING -> {
                                isLoading(true)
                            }

                            StatusResponse.SUCCESS -> {
                                isLoading(false)
                                Toast.makeText(
                                    this,
                                    "Register berhasil",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }

                            StatusResponse.ERROR -> {
                                isLoading(false)
                                Toast.makeText(
                                    this,
                                    "Register gagal",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isFieldValid(etEmail: Editable?, etName: Editable?, etPassword: Editable?, etConfirmPassword: Editable?): Boolean {
        when {
            etEmail.isNullOrEmpty() -> {
                Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail).matches() -> {
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
                return false
            }

            etName.isNullOrEmpty() -> {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.isNullOrEmpty() -> {
                Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            etPassword.length < 8 -> {
                Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show()
                return false
            }

            etConfirmPassword.isNullOrEmpty() -> {
                Toast.makeText(this, "Konfirmasi password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            etConfirmPassword.toString() != etPassword.toString() -> {
                Toast.makeText(this, "Password yang anda masukan tidak cocok", Toast.LENGTH_SHORT).show()
                return false
            }

            else -> return true
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            binding.progressBar.show()
        } else {
            binding.progressBar.hide()
        }
    }
}