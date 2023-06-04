package com.dwi.maurea.ui.market

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        getAllItemSales()

        binding.apply {

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpAdapter() {
        binding.rvMarket.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            this.adapter = itemSalesAdapter
        }
    }

    private fun getAllItemSales() {
        viewModel.getAllItemSales().observe(viewLifecycleOwner) { item ->
            if (item != null) {
                when (item.status) {
                    StatusResponse.LOADING -> {
                        // do nothing
                    }

                    StatusResponse.SUCCESS -> {
                        setUpAdapter()
                        item.body?.item?.let { itemSalesAdapter.setData(it) }
                    }

                    StatusResponse.ERROR -> {
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
                        setUpAdapter()
                        item.body?.item?.let { itemSalesAdapter.setData(it) }
                    }

                    StatusResponse.ERROR -> {
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
}