package dev.capstone.satako_mobile.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.pref.ThemePreference
import dev.capstone.satako_mobile.data.pref.dataStoreTheme
import dev.capstone.satako_mobile.databinding.ActivityMainBinding
import dev.capstone.satako_mobile.presentation.profile.settings.SettingsViewModel
import dev.capstone.satako_mobile.presentation.profile.settings.SettingsViewModelFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(ThemePreference.getInstance(this.dataStoreTheme))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeTheme()
        navigationBar()
    }


    private fun navigationBar() {

        // Set up NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setupWithNavController(navController)

        // Set up custom navigation to prevent fragment recreation and handle back stack
        bottomNavigationView.setOnItemSelectedListener { item ->
            val navOptions = androidx.navigation.navOptions {
                popUpTo(R.id.main_navigation) {
                    inclusive = false
                }
                launchSingleTop = true
            }
            when (item.itemId) {
                R.id.home_fragment -> {
                    navController.navigate(R.id.home_fragment, null, navOptions)
                    true
                }

                R.id.history_fragment -> {
                    navController.navigate(R.id.history_fragment, null, navOptions)
                    true
                }

                R.id.profile_fragment -> {
                    navController.navigate(R.id.profile_fragment, null, navOptions)
                    true
                }

                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment, R.id.history_fragment, R.id.profile_fragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }

                else -> bottomNavigationView.visibility = View.GONE
            }
            logCurrentFragment()
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.history_fragment || navController.currentDestination?.id == R.id.profile_fragment) {
            if (navController.currentDestination?.id != R.id.home_fragment) {
                binding.bottomNavigation.selectedItemId = R.id.home_fragment
                navController.navigate(R.id.home_fragment)
            } else {
                super.onBackPressed() // Exit the app
            }
        } else {
            super.onBackPressed()
        }

        logCurrentFragment()
    }

    private fun logCurrentFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentList: List<Fragment> = fragmentManager.fragments
        val backStackCount = fragmentManager.backStackEntryCount

        Log.i("MainActivity", "Active fragments: ${fragmentList.size}")
        Log.i("MainActivity", "Back stack entry count: $backStackCount")
        fragmentList.forEach { fragment ->
            Log.i("MainActivity", "Fragment: ${fragment.javaClass.simpleName}")
        }
    }

    private fun changeTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            viewModel.getThemeLiveData().observe(this) {
                when (it) {
                    0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

}