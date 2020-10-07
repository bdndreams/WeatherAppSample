package com.bdn.weathersample.ui

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.bdn.weathersample.R
import com.bdn.weathersample.common.Constants
import com.bdn.weathersample.common.viewModelFactory
import com.bdn.weathersample.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException
import java.lang.Exception
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapViewModel : MapViewModel
    private lateinit var mMap: GoogleMap
    lateinit var toolbar: Toolbar
    lateinit var sharedpreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        sharedpreference = getSharedPreferences(
            Constants.SHARED_PREFERENCE,
            Context.MODE_PRIVATE
        )
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Maps"
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mapViewModel =
            ViewModelProvider(this, viewModelFactory {
                MapViewModel(
                    application
                )
            }).get(MapViewModel::class.java)

        observeSavingState()
        btnSave.setOnClickListener {
            mapViewModel.saveCity()
        }
        mapViewModel.isSaveButtonEnabled.observe(this, Observer {
            btnSave.isEnabled = it?:false
        })
    }

    private fun observeSavingState(){
        mapViewModel.savingState.observe(this, Observer{ state->
            state?.let {
                when (it) {
                    MapViewModel.SavedState.Loading->{}
                    MapViewModel.SavedState.Success->{
                        finish()
                    }
                    MapViewModel.SavedState.Error->{
                        Toast.makeText(this@MapsActivity, "Saved Successful", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val city = LatLng(
            sharedpreference.getString("latitude", "-34.0")!!.toDouble(),
            sharedpreference.getString("longitude", "151.0")!!.toDouble()
        )//151.0
        mMap.addMarker(
            MarkerOptions().position(city).draggable(true).title("${sharedpreference.getString("city", "sydney")}")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 10f))

        mMap.uiSettings.isZoomControlsEnabled = true;
        mMap.setOnMarkerDragListener(DragListener(this, sharedpreference, mapViewModel))
    }

    class DragListener(
        private val context: Context,
        private val sharedpreference: SharedPreferences,
        private val mapViewModel: MapViewModel
    ) : GoogleMap.OnMarkerDragListener{

        override fun onMarkerDragEnd(marker: Marker?) {
            val latLng: LatLng? = marker?.position
            val geoCoder = Geocoder(context, Locale.getDefault())
            latLng?.let {
                try {
                    mapViewModel.isSaveButtonEnabled.value = true
                    mapViewModel.lat = latLng.latitude
                    mapViewModel.lng = latLng.longitude
                    sharedpreference.edit().putString("latitude", "${it.latitude}").apply()
                    sharedpreference.edit().putString("longitude", "${it.longitude}").apply()

                    val address =
                        geoCoder.getFromLocation(it.latitude, it.longitude, 1)[0]

                    Log.d("Bdn", address.locality?:"")

                    val name = address.locality?:"UnKnown"
                    marker.title = name
                    mapViewModel.cityName = name

                } catch (e: IOException) {
                    Log.e("Bdn", "$e")
                } catch (e: NullPointerException){
                    Log.e("Bdn", "$e")
                }catch (e: Exception){
                    Log.e("Bdn", "$e")
                }
            }
        }

        override fun onMarkerDragStart(p0: Marker?) {

        }

        override fun onMarkerDrag(p0: Marker?) {

        }

    }
}
