package com.dwi.maurea.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.dwi.maurea.data.local.ItemScan
import com.dwi.maurea.data.local.ItemScanData
import com.dwi.maurea.databinding.ActivityMainBinding
import com.dwi.maurea.ui.detection.DetectionActivity
import com.dwi.maurea.utils.Constanta

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var list: ArrayList<ItemScan> = arrayListOf()
    private lateinit var itemPopularAdapter: ItemPopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemPopularAdapter = ItemPopularAdapter()

        setUpProfile()

        list.addAll(ItemScanData.listData)
        setUpAdapter()
        itemPopularAdapter.setData(list)
        itemPopularAdapter.setOnItemClickCallback(object : ItemPopularAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemScan) {
                val intent = Intent(this@MainActivity, DetectionActivity::class.java)
                intent.putExtra(Constanta.MODEL, data.nama)
                startActivity(intent)
            }

        })

    }

    private fun setUpAdapter() {
        binding.rvPopular.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
            this.adapter = itemPopularAdapter
        }
    }

    private fun setUpProfile() {
        binding.apply {
            ivProfile.load("https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&q=80&w=1887&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
        }
    }
}