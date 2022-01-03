package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.domain.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import com.example.unsplashphotos.utils.DataState
import javax.inject.Inject

class FetchPhotoUseCaseImpl @Inject constructor(
    private val photoRepo: PhotoRepo
) : FetchPhotoUseCase {
    override suspend fun fetchPhotos(page: Int, perPage: Int): DataState<List<Photo>> =
        photoRepo.getPhotos(page, perPage)
}
