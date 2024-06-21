package dev.capstone.satako_mobile.presentation.profile.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.capstone.satako_mobile.data.pref.ThemePreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themePreference: ThemePreference
) : ViewModel() {
    private val _themeMode = MutableStateFlow<Int?>(null)
    val themeMode: StateFlow<Int?> = _themeMode

    init {
        viewModelScope.launch {
            themePreference.getTheme().collect { themeMode ->
                _themeMode.value = themeMode
            }
        }
    }

    suspend fun getThemeSettings(): Int? {
        return themePreference.getTheme().first()
    }

    fun getThemeLiveData(): LiveData<Int?> {
        return themePreference.getTheme().asLiveData()
    }


    fun saveTheme(isDarkMode: Int?) {
        viewModelScope.launch {
            themePreference.saveTheme(isDarkMode)
        }
    }
}