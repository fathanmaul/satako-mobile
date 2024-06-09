package dev.capstone.satako_mobile.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.capstone.satako_mobile.data.repository.DataRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: DataRepository) : ViewModel() {

    fun logout(callback: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            callback()
        }
    }

}