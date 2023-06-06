package com.dwi.maurea.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dwi.maurea.databinding.FragmentProfileBinding
import com.dwi.maurea.ui.login.LoginActivity
import com.dwi.maurea.utils.Constanta.ACCESS_TOKEN
import com.dwi.maurea.utils.Constanta.EMAIL
import com.dwi.maurea.utils.Constanta.PASSWORD
import com.dwi.maurea.utils.Constanta.USERNAME
import com.dwi.maurea.utils.SharedPrefUtils

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()

        binding.btnKeluar.setOnClickListener {
            logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProfile() {
        val name = SharedPrefUtils.getString(USERNAME)
        val email = SharedPrefUtils.getString(EMAIL)

        binding.apply {
            etName.setText(name)
            etEmail.setText(email)
        }
    }

    private fun logout() {
        SharedPrefUtils.remove(EMAIL)
        SharedPrefUtils.remove(PASSWORD)
        startActivity(Intent(requireContext(), LoginActivity::class.java))
    }
}