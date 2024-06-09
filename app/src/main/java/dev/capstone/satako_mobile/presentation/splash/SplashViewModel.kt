package dev.capstone.satako_mobile.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.capstone.satako_mobile.data.repository.DataRepository

class SplashViewModel(private val repository: DataRepository) : ViewModel() {

    fun getSession(): LiveData<String> {
       return repository.getSession().asLiveData()
    }


}