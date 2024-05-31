package dev.capstone.satako_mobile.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.ActivityMainBinding
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigationBar()
    }


    private fun navigationBar() {

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigation

        NavigationUI.setupWithNavController(
            bottomNavigationView, navController
        )

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val currentDestinationId = navController.currentDestination?.id
            when (item.itemId) {
                R.id.home_fragment -> {
                    if (currentDestinationId != R.id.home_fragment) {
                        navController.navigate(R.id.home_fragment)
                    }
                    true
                }
                R.id.history_fragment -> {
                    if (currentDestinationId != R.id.history_fragment) {
                        navController.navigate(R.id.history_fragment)
                    }
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment, R.id.history_fragment -> binding.bottomNavigation.show()

                else -> binding.bottomNavigation.gone()
            }
        }


    }


}