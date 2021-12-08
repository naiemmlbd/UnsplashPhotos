package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.usecase.PhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val photoUseCase: PhotoUseCase) : ViewModel() {

    fun fetchPhotos(): Flow<PagingData<Photo>> {
        return photoUseCase.fetchPhotos()
    }
}
