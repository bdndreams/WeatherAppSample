package com.bdn.weathersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.FragmentHelpBinding
import com.bdn.weathersample.databinding.FragmentSettingsBinding
import com.bdn.weathersample.viewmodel.HelpViewModel
import com.bdn.weathersample.viewmodel.SettingsViewModel

class HelpFragment: Fragment() {

    private lateinit var binding: FragmentHelpBinding

    private lateinit var viewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelFactory {
            HelpViewModel()
        }).get(HelpViewModel::class.java)

        binding = FragmentHelpBinding.inflate(inflater, container, false)

        binding.webView.loadDataWithBaseURL(null, viewModel.htmlDocument, "text/HTML", "UTF-8", null)

        return binding.root
    }
}