package com.dwi.maurea.ui.scan.fruits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dwi.maurea.data.remote.response.item.ItemScan
import com.dwi.maurea.databinding.ItemFruitsBinding
import com.dwi.maurea.utils.DiffUtils
import com.dwi.maurea.utils.ObjectDetectorUtils

class FruitsChoiceAdapter : RecyclerView.Adapter<FruitsChoiceAdapter.FruitsChoiceViewHolder>() {
    private var oldList = emptyList<ItemScan>()
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class FruitsChoiceViewHolder(private val binding: ItemFruitsBinding) :
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FruitsChoiceAdapter.FruitsChoiceViewHolder {
        val itemFruitsBinding =
            ItemFruitsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitsChoiceViewHolder(itemFruitsBinding)
    }

    override fun onBindViewHolder(
        holder: FruitsChoiceAdapter.FruitsChoiceViewHolder,
        position: Int
    ) {
        val item = oldList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = oldList.size

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
        fun onItemClicked(item: ItemScan)
    }
}