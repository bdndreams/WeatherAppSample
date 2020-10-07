package com.bdn.weathersample.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bdn.weathersample.R
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.databinding.ActivitySplashBinding
import com.bdn.weathersample.viewmodel.SplashActivityViewModel
import com.bdn.weathersample.viewmodel.SplashState


/**
 * Splash screen activity of the application
 */
class SplashActivity : AppCompatActivity() {

    var perms =
        arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)

    private var permsRequestCode = 200
    private var realBinding: ActivitySplashBinding? = null
    private val mMainBinding: ActivitySplashBinding
        get() = realBinding
            ?: throw IllegalStateException("RuntimeException")
    private lateinit var splashViewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )

        splashViewModel =
            ViewModelProvider(this, viewModelFactory {
                SplashActivityViewModel(
                    application
                )
            }).get(SplashActivityViewModel::class.java)

        //Observe the splashState live data to move to next activity
        splashViewModel.splashStateData.observe(this, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    goToMainActivity()
                }
            }
        })

        if(!checkPermission()){
            requestPermission()
        }else{
            splashViewModel.timer()
        }
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            perms[0]
        )
        val result1 = ContextCompat.checkSelfPermission(applicationContext, perms[1])
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            perms,
            permsRequestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            permsRequestCode -> if (grantResults.size > 0) {
                val readAccepted =
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

                if (readAccepted) {
                    splashViewModel.timer()
                }
                else {
                    finish()
                }
            }
        }
    }
    /**
     * Launch MainActivity
     */
    private fun goToMainActivity() {
        startActivity(Intent(this, NavDrawerActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        realBinding = null
    }
}
