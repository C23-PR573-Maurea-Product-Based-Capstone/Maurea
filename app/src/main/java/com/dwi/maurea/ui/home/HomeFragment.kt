package com.dwi.maurea.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dwi.maurea.databinding.FragmentHomeBinding
import com.dwi.maurea.ui.detection.DetectionActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabScan.setOnClickListener {
            Intent(requireContext(), DetectionActivity::class.java).also {
                startActivity(it)
            }
        }

        getProfile()
    }

    private fun getProfile() {
        viewModel.getProfile().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                binding.tvName.text = profile.name
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}