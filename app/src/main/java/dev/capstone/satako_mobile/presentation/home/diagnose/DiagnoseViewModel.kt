package dev.capstone.satako_mobile.presentation.home.diagnose

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.capstone.satako_mobile.data.repository.DataRepository
import okhttp3.MultipartBody

class DiagnoseViewModel(private val repository: DataRepository) : ViewModel() {
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    fun setImageUri(uri: Uri?) {
        _imageUri.value = uri
    }

    fun predict(file: MultipartBody.Part) = repository.predict(file)
}