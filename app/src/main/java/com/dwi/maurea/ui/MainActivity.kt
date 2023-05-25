package com.dwi.maurea.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dwi.maurea.R
import com.dwi.maurea.databinding.ActivityMainBinding
import com.dwi.maurea.utils.Constanta
import com.dwi.maurea.utils.SharedPrefUtils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navView: BottomNavigationView by lazy { binding.bottomNavigation}
    private val navController by lazy {findNavController(R.id.nav_host_fragment)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("MainActivity", "token : ${SharedPrefUtils.getString(Constanta.ACCESS_TOKEN)}")

        navView.setupWithNavController(navController)
    }
}