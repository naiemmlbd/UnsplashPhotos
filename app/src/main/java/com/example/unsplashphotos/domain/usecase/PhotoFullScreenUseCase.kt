package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject


class PhotoFullScreenUseCase @Inject constructor(private val photoRepo: PhotoRepo) {

    suspend fun execute(photoId: String): Photo?= photoRepo.getPhotoById(photoId)
}
