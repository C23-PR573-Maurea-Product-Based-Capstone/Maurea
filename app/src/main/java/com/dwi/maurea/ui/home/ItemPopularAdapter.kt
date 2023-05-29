package com.dwi.maurea.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dwi.maurea.data.remote.response.item.ItemSalesPop
import com.dwi.maurea.databinding.ItemSaleBinding
import com.dwi.maurea.utils.DiffUtils

class ItemPopularAdapter : RecyclerView.Adapter<ItemPopularAdapter.ItemViewHolder>() {
    private var oldList = emptyList<ItemSalesPop>()

    inner class ItemViewHolder(private val binding: ItemSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemSalesPop) {
            binding.apply {
                ivItemSale.load(item.gambar)
                tvName.text = item.nama
                tvPrice.text = item.harga
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemSaleBinding =
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemSaleBinding)
    }

    override fun getItemCount(): Int = oldList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
    }

    fun setData(newList: List<ItemSalesPop>) {
        val diffCallback = DiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }


}