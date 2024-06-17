package dev.capstone.satako_mobile.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.capstone.satako_mobile.di.Injection
import dev.capstone.satako_mobile.presentation.history.HistoryViewModel
import dev.capstone.satako_mobile.presentation.home.diagnose.DiagnoseViewModel
import dev.capstone.satako_mobile.presentation.login.LoginViewModel
import dev.capstone.satako_mobile.presentation.profile.ProfileViewModel
import dev.capstone.satako_mobile.presentation.profile.settings.SettingsViewModel
import dev.capstone.satako_mobile.presentation.register.RegisterViewModel
import dev.capstone.satako_mobile.presentation.splash.SplashViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(Injection.provideRepository(context)) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(Injection.provideRepository(context)) as T
            }

            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(Injection.provideRepository(context)) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

}