package com.dwi.maurea.ui.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dwi.maurea.data.remote.response.item.ItemSales
import com.dwi.maurea.data.remote.response.item.ItemSalesPop
import com.dwi.maurea.databinding.ItemSaleBinding
import com.dwi.maurea.utils.DiffUtils

class ItemSalesAdapter : RecyclerView.Adapter<ItemSalesAdapter.ItemSalesViewHolder>() {
    private var oldList = emptyList<ItemSales>()
    inner class ItemSalesViewHolder(private val binding: ItemSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemSales) {
            binding.apply {
                ivItemSale.load(item.gambar)
                tvName.text = item.nama
                tvPrice.text = item.harga
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSalesViewHolder {
        val itemSaleBinding =
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSalesViewHolder(itemSaleBinding)
    }

    override fun getItemCount(): Int = oldList.size

    override fun onBindViewHolder(holder: ItemSalesViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
    }

    fun setData(newList: List<ItemSales>) {
        val diffCallback = DiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}