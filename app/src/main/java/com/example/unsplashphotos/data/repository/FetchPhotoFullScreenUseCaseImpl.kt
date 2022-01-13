package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.domain.usecase.FetchPhotoFullScreenUseCase
import com.example.unsplashphotos.utils.DataState
import javax.inject.Inject

class FetchPhotoFullScreenUseCaseImpl @Inject constructor(private val photoRepo: PhotoRepo): FetchPhotoFullScreenUseCase {
    override suspend fun getPhoto(photoId: String): DataState<Photo> = photoRepo.getPhotoById(photoId)
}
