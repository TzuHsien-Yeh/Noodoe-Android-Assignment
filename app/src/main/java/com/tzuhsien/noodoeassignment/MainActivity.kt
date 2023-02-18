package com.tzuhsien.noodoeassignment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tzuhsien.noodoeassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.loginFragment -> {
                    toolbar.visibility = View.GONE
                }

                R.id.parkingInfoFragment -> {
                    toolbar.visibility = View.VISIBLE
                    toolbar.navigationIcon = null
                }

                R.id.timeZoneFragment ->  {
                    toolbar.visibility = View.VISIBLE
                    toolbar.setNavigationIcon(R.drawable.arrow_left)
                    toolbar.setNavigationOnClickListener { onBackPressed() }
                    binding.toolbarTextTimeZone.visibility = View.GONE
                }
            }
        }

    }

    override fun onBackPressed() {
        navController.navigateUp()
    }
}