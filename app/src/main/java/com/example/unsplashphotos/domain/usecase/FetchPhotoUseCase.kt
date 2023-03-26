package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.utils.DataState

interface FetchPhotoUseCase {
    suspend fun fetchPhotos(page: Int, perPage: Int): DataState<List<Photo>>
}
