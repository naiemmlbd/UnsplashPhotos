package com.example.unsplashphotos.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.unsplashphotos.domain.usecase.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val photoUseCase: PhotoUseCase) : ViewModel() {

    fun getPhotos() = liveData {
        val photoList = photoUseCase.getPhotos()
        Log.d("===>", "viewModel " + photoList?.size)
        emit(photoList)
    }
}