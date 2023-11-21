package com.example.thoughtctl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImgurViewModelFactory(private val repository: ImgurRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImgurViewModel::class.java)) {
            return ImgurViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
