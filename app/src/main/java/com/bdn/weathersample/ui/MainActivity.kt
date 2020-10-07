package com.bdn.weathersample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.bdn.weathersample.R
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.ActivityMainBinding
import com.bdn.weathersample.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var realBinding: ActivityMainBinding
    lateinit var mMainActivityViewModel: MainViewModel
    lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        mMainActivityViewModel =
            ViewModelProvider(this, viewModelFactory {
                MainViewModel(
                    application
                )
            }).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            setupNavigationController()
        }
    }

    private fun setupNavigationController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        mNavController = navHostFragment.navController
//        mNavController = findNavController(R.id.nav_host_fragment)

    }
}