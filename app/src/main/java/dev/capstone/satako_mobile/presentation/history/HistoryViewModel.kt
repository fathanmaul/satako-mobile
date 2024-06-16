package dev.capstone.satako_mobile.presentation.history

import androidx.lifecycle.ViewModel
import dev.capstone.satako_mobile.data.repository.DataRepository

class HistoryViewModel(private val repository: DataRepository): ViewModel() {
    fun getHistory() = repository.getHistory()
}