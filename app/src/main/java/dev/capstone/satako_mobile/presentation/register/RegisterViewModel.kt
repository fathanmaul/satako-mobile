package dev.capstone.satako_mobile.presentation.register

import androidx.lifecycle.ViewModel
import dev.capstone.satako_mobile.data.repository.DataRepository

class RegisterViewModel(private val repository: DataRepository) : ViewModel() {

    fun register(name: String, email: String, password: String, confirmPassword: String) =
        repository.register(name, email, password, confirmPassword)
}