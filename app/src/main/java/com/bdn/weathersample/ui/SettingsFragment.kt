package com.bdn.weathersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.FragmentSettingsBinding
import com.bdn.weathersample.viewmodel.SettingsViewModel

class SettingsFragment: Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory {
            SettingsViewModel()
        }).get(SettingsViewModel::class.java)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }
}