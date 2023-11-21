package com.example.thoughtctl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ImgurViewModel(private val repository: ImgurRepository) : ViewModel() {

    // LiveData for observing the search results
    val imgurImages = MutableLiveData<List<ImgurImage>>()

    // Function to search for images
    fun searchImages(query: String) {
        viewModelScope.launch {
            try {
                val result = repository.getTopImages(query)
                imgurImages.postValue(result)
            } catch (e: Exception) {
                // Handle errors
                e.printStackTrace()
            }
        }
    }
}
