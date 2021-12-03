package com.example.unsplashphotos.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.unsplashphotos.domain.usecase.PhotoUseCase


class PhotoViewModel(private val photoUseCase: PhotoUseCase) : ViewModel() {

    fun getPhotos() = liveData {
        val photoList = photoUseCase.getPhotos()
        Log.d("===>", "viewModel " + photoList?.size)
        emit(photoList)
    }
}