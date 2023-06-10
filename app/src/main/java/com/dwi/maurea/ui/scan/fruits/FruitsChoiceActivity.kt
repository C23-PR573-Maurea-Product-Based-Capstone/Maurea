package com.dwi.maurea.ui.scan.fruits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dwi.maurea.R
import com.dwi.maurea.data.remote.response.StatusResponse
import com.dwi.maurea.data.remote.response.item.ItemScan
import com.dwi.maurea.databinding.ActivityFruitsChoiceBinding
import com.dwi.maurea.ui.scan.detection.DetectionActivity
import com.dwi.maurea.utils.Constanta.MODEL

class FruitsChoiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFruitsChoiceBinding
    private val viewModel: FruitsChoiceViewModel by viewModels()
    private lateinit var fruitsChoiceAdapter: FruitsChoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFruitsChoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fruitsChoiceAdapter = FruitsChoiceAdapter()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        getFruitChoices()
    }

    private fun getFruitChoices() {
        viewModel.getFruits().observe(this) { choice ->
            if (choice != null) {
                when (choice.status) {
                    StatusResponse.LOADING -> {
                        isLoading(true)
                    }

                    StatusResponse.SUCCESS -> {
                        isLoading(false)
                        setUpAdapter()
                        fruitsChoiceAdapter.setData(choice.body?.scannableitem!!)
                        fruitsChoiceAdapter.setOnItemClickCallback(object : FruitsChoiceAdapter.OnItemClickCallback{
                            override fun onItemClicked(item: ItemScan) {
                                val intent = Intent(this@FruitsChoiceActivity, DetectionActivity::class.java)
                                intent.putExtra(MODEL, item.nama)
                                startActivity(intent)
                            }

                        })
                    }

                    StatusResponse.ERROR -> {
                        isLoading(false)
                        Toast.makeText(this, "Terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {
        binding.rvFruits.apply {
            layoutManager = GridLayoutManager(this@FruitsChoiceActivity, 2)
            setHasFixedSize(true)
            this.adapter = fruitsChoiceAdapter
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            binding.progressbar.show()
        } else {
            binding.progressbar.hide()
        }
    }
}