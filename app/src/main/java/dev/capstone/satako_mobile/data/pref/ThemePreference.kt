package dev.capstone.satako_mobile.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStoreTheme: DataStore<Preferences> by preferencesDataStore(name = "themes")

class ThemePreference private constructor(private val dataStoreTheme: DataStore<Preferences>) {


    fun getTheme(): Flow<Int?> {
        return dataStoreTheme.data.map { preferences ->
            preferences[THEME_KEY] // If THEME_KEY is not present, this will be null
        }
    }

    suspend fun saveTheme(isDarkMode: Int?) {
        dataStoreTheme.edit { preferences ->
            if (isDarkMode != null) {
                preferences[THEME_KEY] = isDarkMode
            } else {
                preferences.remove(THEME_KEY)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreference? = null

        private val THEME_KEY = intPreferencesKey("theme")

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}