package dev.capstone.satako_mobile.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.capstone.satako_mobile.data.repository.DataRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: DataRepository) : ViewModel() {

    fun login(email: String, password: String) = repository.login(email, password)


    fun saveTokenSession(token: String, callback: () -> Unit) {
        viewModelScope.launch {
            repository.saveSession(token)
            callback()
        }

    }
}