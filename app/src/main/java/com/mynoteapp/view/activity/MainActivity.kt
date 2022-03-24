package com.mynoteapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.mynoteapp.R
import com.mynoteapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // VIEW BINDING
    private lateinit var binding: ActivityMainBinding

    // NAVIGATION COMPONENT NAVHOST
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // IMPLEMENT THE VIEW BINDING
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IMPLEMENT THE NAV CONTROLLER
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

    }

    // IMPLEMENT THE BACK BUTTON IN THE TOOLBAR
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.mainNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}