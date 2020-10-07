package com.bdn.weathersample.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.bdn.weathersample.R
import com.bdn.weathersample.database.model.CityModel
import com.google.android.material.navigation.NavigationView

class NavDrawerActivity: AppCompatActivity(),
NavigationView.OnNavigationItemSelectedListener {
    var toolbar: Toolbar? = null

    var drawerLayout: DrawerLayout? = null

    var navController: NavController? = null

    var navigationView: NavigationView? = null

    var mSelectedCity: CityModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)
        setupNavigation()
    }

    // Setting Up One Time Navigation
    private fun setupNavigation() {
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigationView)
        navController = Navigation.findNavController(this, R.id.nav_host)
        navController?.let {
            NavigationUI.setupActionBarWithNavController(this, it, drawerLayout)
            navigationView?.let {navView->
                NavigationUI.setupWithNavController(navView, it)
                navView.setNavigationItemSelectedListener(this)
            }

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.nav_host), drawerLayout
        )
    }

    override fun onBackPressed() {

        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout?.closeDrawers()
        val id = menuItem.itemId
        when (id) {
            R.id.itm_home -> navController?.navigate(R.id.fragmentHome)
            R.id.itm_Setting -> navController?.navigate(R.id.settingsFragment)
            R.id.itm_help -> navController?.navigate(R.id.helpFragment)
            R.id.itm_maps -> {
                var intent = Intent(this@NavDrawerActivity, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}