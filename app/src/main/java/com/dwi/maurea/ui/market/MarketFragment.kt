package com.dwi.maurea.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dwi.maurea.R
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.databinding.FragmentMarketBinding
import com.google.android.material.snackbar.Snackbar

class MarketFragment : Fragment() {
    private var _binding: FragmentMarketBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MarketViewModel by viewModels()
    private lateinit var itemSalesAdapter: ItemSalesAdapter
    private lateinit var itemSearchAdapter: ItemSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSalesAdapter = ItemSalesAdapter()
        itemSearchAdapter = ItemSearchAdapter()
        setUpAdapterAllItems()
        getAllItemSales()

        binding.apply {
            svMarket.clearFocus()
            svMarket.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    svMarket.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        setUpAdapterSearchItems()
                        getSearchItems(newText)
                    }
                    return false
                }

            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAdapterAllItems() {
        binding.rvMarket.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            this.adapter = itemSalesAdapter
        }
    }

    private fun setUpAdapterSearchItems() {
        binding.rvMarket.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            this.adapter = itemSearchAdapter
        }
    }

    private fun getAllItemSales() {
        viewModel.getAllItemSales().observe(viewLifecycleOwner) { item ->
            if (item != null) {
                when (item.status) {
                    StatusResponse.LOADING -> {
                        isLoading(true)
                    }

                    StatusResponse.SUCCESS -> {
                        isLoading(false)
                        item.body?.item?.let { itemSalesAdapter.setData(it) }
                    }

                    StatusResponse.ERROR -> {
                        isLoading(false)
                        Snackbar.make(
                            requireView(),
                            "Terjadi kesalahan",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getSearchItems(query: String) {
        viewModel.getSearchItems(query).observe(viewLifecycleOwner) { item ->
            if (item != null) {
                when (item.status) {
                    StatusResponse.LOADING -> {
                        // do nothing
                    }

                    StatusResponse.SUCCESS -> {
                        item.body?.let { itemSearchAdapter.setData(it) }
                    }

                    StatusResponse.ERROR -> {
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