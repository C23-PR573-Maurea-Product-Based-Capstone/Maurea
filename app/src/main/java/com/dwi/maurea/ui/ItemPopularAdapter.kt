package com.dwi.maurea.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dwi.maurea.data.local.ItemScan
import com.dwi.maurea.databinding.ItemFruitsBinding
import com.dwi.maurea.utils.DiffUtils

class ItemPopularAdapter : RecyclerView.Adapter<ItemPopularAdapter.ItemViewHolder>() {
    private var oldList = emptyList<ItemScan>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ItemViewHolder(private val binding: ItemFruitsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemScan) {
            binding.apply {
                tvItemName.text = item.nama
                ivItemFruits.load(item.gambar)
            }

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemFruitsBinding =
            ItemFruitsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(itemFruitsBinding)
    }

    override fun getItemCount(): Int = oldList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = oldList[position]
        holder.bind(item)
    }

    fun setData(newList: List<ItemScan>) {
        val diffCallback = DiffUtils(oldList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemScan)
    }


}