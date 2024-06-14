package dev.capstone.satako_mobile.presentation.profile.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.capstone.satako_mobile.data.pref.ThemePreference
import dev.capstone.satako_mobile.utils.Const
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themePreference: ThemePreference,
) : ViewModel() {
    fun getThemeSettings(): LiveData<String> {
        return themePreference.getThemePref().asLiveData()
    }

    suspend fun saveThemeSetting(
        theme: Const.Theme
    ) {
        viewModelScope.launch {
            themePreference.saveThemePref(theme)
        }
    }
}