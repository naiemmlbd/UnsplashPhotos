package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import javax.inject.Inject

class FetchPhotoUseCaseImpl @Inject constructor(
    private val photoRepo: PhotoRepo
) : FetchPhotoUseCase {
    override suspend fun fetchPhotos(page: Int, perPage: Int): List<Photo>? =
        photoRepo.getPhotos(page, perPage)
}
