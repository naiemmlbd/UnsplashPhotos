package com.example.unsplashphotos.domain.usecase


import com.example.unsplashphotos.data.model.local.Photo


interface FetchPhotoUseCase {
    suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo>?
}
