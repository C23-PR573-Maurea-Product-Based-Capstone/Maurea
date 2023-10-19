package com.dwi.maurea.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dwi.maurea.R
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.databinding.FragmentHomeBinding
import com.dwi.maurea.ui.scan.detection.DetectionActivity
import com.dwi.maurea.ui.scan.fruits.FruitsChoiceActivity
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var itemPopularAdapter: ItemPopularAdapter


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
        itemPopularAdapter = ItemPopularAdapter()
        binding.fabScan.setOnClickListener {
            Intent(requireContext(), DetectionActivity::class.java).also {
                startActivity(it)
            }
        }

        getProfile()
        getItemPopular()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProfile() {
        viewModel.getProfile().observe(viewLifecycleOwner) { profile ->
            if (profile != null) {
                binding.tvName.text = profile.name
            }
        }
    }

    private fun setUpAdapter() {
        binding.rvPopular.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            this.adapter = itemPopularAdapter
        }
    }

    private fun getItemPopular() {
        viewModel.getPopularItems().observe(viewLifecycleOwner) { items ->
            if (items != null) {
                when (items.status) {
                    StatusResponse.LOADING -> {
                        isLoading(true)
                    }

                    StatusResponse.SUCCESS -> {
                        isLoading(false)
                        setUpAdapter()
                        items.body?.popItem?.let { itemPopularAdapter.setData(it) }
                    }

                    StatusResponse.ERROR -> {
                        isLoading(false)
                        Snackbar.make(
                            requireView(),
                            getString(R.string.error_network),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
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