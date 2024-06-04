package dev.capstone.satako_mobile.di

import android.content.Context
import android.util.Log
import dev.capstone.satako_mobile.data.pref.UserPreference
import dev.capstone.satako_mobile.data.pref.dataStore
import dev.capstone.satako_mobile.data.repository.DataRepository
import dev.capstone.satako_mobile.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getSession().first() }
        Log.d("Injection", "token: $token")
        val apiService = ApiConfig.getApiService(token)
        return DataRepository.getInstance(pref, apiService)
    }
}