package com.dwi.maurea.ui.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dwi.maurea.data.remote.response.item.SearchItem
import com.dwi.maurea.databinding.ItemSaleBinding
import com.dwi.maurea.utils.DiffUtils

class ItemSearchAdapter: RecyclerView.Adapter<ItemSearchAdapter.ItemSearchViewHolder>() {
    private var oldList = emptyList<SearchItem>()

    inner class ItemSearchViewHolder (private val binding: ItemSaleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem) {
            binding.apply {
                ivItemSale.load(item.gambar)
                tvName.text = item.nama
                tvPrice.text = item.harga
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSearchViewHolder {
        val itemSearchBinding =
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemSearchViewHolder(itemSearchBinding)
    }

    override fun getItemCount(): Int = oldList.size

    override fun onBindViewHolder(holder: ItemSearchViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
    }

    fun setData(newList: ArrayList<SearchItem>) {
        val diffCallback = DiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}