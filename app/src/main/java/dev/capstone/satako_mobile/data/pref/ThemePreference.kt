package dev.capstone.satako_mobile.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.capstone.satako_mobile.utils.Const
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStoreTheme: DataStore<Preferences> by preferencesDataStore(name = "themes")

class ThemePreference private constructor(private val dataStoreTheme: DataStore<Preferences>) {

    fun getThemePref(): Flow<String> {
        return dataStoreTheme.data.map { preferences ->
            preferences[THEME_KEY] ?: Const.Theme.SYSTEM.value
        }
    }


    suspend fun saveThemePref(
        theme: Const.Theme
    ) {
        dataStoreTheme.edit { preferences ->
            preferences[THEME_KEY] = theme.value
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreference? = null

        private val THEME_KEY = stringPreferencesKey("theme")

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreference {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}