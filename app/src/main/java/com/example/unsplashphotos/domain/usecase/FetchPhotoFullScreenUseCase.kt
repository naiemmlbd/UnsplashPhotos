package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.utils.DataState

interface FetchPhotoFullScreenUseCase {
    suspend fun getPhoto(photoId: String): DataState<Photo>
}
