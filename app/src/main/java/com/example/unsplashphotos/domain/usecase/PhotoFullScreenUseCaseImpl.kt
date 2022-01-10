package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.utils.DataState
import javax.inject.Inject


class PhotoFullScreenUseCaseImpl @Inject constructor(private val photoRepo: PhotoRepo) :
    FetchPhotoFullScreenUseCase {

    override suspend fun getPhoto(photoId: String): DataState<Photo> =
        photoRepo.getPhotoById(photoId)
}
