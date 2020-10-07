package com.bdn.weathersample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.FragmentWeatherDetailBinding
import com.bdn.weathersample.viewmodel.WeatherDetailViewModel
import com.bdn.weathersample.viewmodel.setVisibility
import java.text.SimpleDateFormat
import java.util.*

class WeatherDetailFragment: Fragment() {

    private lateinit var binding: FragmentWeatherDetailBinding

    private lateinit var viewModel: WeatherDetailViewModel
    private val windUnit = "m/s"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val cityModel = (requireActivity() as NavDrawerActivity).mSelectedCity
        (requireActivity() as NavDrawerActivity).mSelectedCity = null

        viewModel = ViewModelProvider(this, viewModelFactory {
            WeatherDetailViewModel(cityModel)
        }).get(WeatherDetailViewModel::class.java)

        binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)

        observeProgressVisibility()
        observeWeatherChange()
        return binding.root
    }

    private fun observeProgressVisibility(){
        viewModel.isProgressHide.observe(viewLifecycleOwner, Observer {
            if(it == true){
                binding.relativeCover.setVisibility(false)
                binding.mainContent.setVisibility(true)
            }else{
                binding.relativeCover.setVisibility(true)
                binding.mainContent.setVisibility(false)
            }
        })
    }

    private fun observeWeatherChange(){
        viewModel.weatherData.observe(viewLifecycleOwner, Observer {
            it?.let { data->
                binding.txtCityName.text = data.name
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val date = cal.get(Calendar.DAY_OF_MONTH)
                val day = SimpleDateFormat(
                    "EEEE",
                    Locale.ENGLISH
                ).format(cal.getTime())//LocalDate.now().getDayOfWeek().name()
                val month =
                    SimpleDateFormat("MMMM").format(cal.getTime())
                val findate = "${day},${month}" + "  " + "${date},${year}"
                binding.txtDate.text = findate
                binding.textTemp.text = data.main.temp.toString()
                binding.txtTempMin.text = data.main.min.toString()
                binding.txtTempMax.text = data.main.max.toString()
                binding.txtWeatherType.text = data.weather[0].main
                binding.txtWind.text = "${data.wind.speed}  $windUnit"
                binding.txtHumidity.text = "${data.main.humidity.toString()}%"
                val sdf = SimpleDateFormat("HH:mm")
                val unix = data.sys.sunrise
                val sunrise = java.util.Date(unix.toLong() * 1000)
                binding.txtSunrise.text = sdf.format(sunrise)
                val sdf2 = SimpleDateFormat("HH:mm")
                val unix2 = data.sys.sunset
                val sunset = java.util.Date(unix2.toLong() * 1000)
                binding.txtSunset.text = sdf2.format(sunset)

            }
        })
    }


}