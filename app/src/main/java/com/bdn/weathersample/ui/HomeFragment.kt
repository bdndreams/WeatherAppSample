package com.bdn.weathersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bdn.weathersample.R
import com.bdn.weathersample.adapter.CityAdapter
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.FragmentHomeBinding
import com.bdn.weathersample.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var cityAdapter: CityAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, viewModelFactory {
            HomeViewModel(application = requireActivity().application
            )
        }).get(HomeViewModel::class.java)
        viewModel.title = "Weather App"

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        cityAdapter = CityAdapter(viewModel)
        binding.rvCityList.adapter = cityAdapter
        viewModel.loadBookmarkCities()

        observeCityList()
        observeCityChangeSelection()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadBookmarkCities()
    }

    private fun observeCityList(){
        viewModel.cityList.observe(viewLifecycleOwner, Observer {
            it?.let {list->
                cityAdapter.updateData(list)
            }
        })
    }

    private fun observeCityChangeSelection(){
        viewModel.cityModelLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                (requireActivity() as NavDrawerActivity).mSelectedCity = it
                (requireActivity() as NavDrawerActivity).navController?.navigate(R.id.action_fragmentHome_to_weatherDetailFragment)
                viewModel.cityModelLiveData.value = null
            }
        })
    }

}