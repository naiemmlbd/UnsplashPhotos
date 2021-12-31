package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject


class GetPhotoFullScreenUseCase @Inject constructor(private val photoRepo: PhotoRepo) {

    suspend fun execute(photoId: String): PhotoRemoteEntity?= photoRepo.getPhotoById(photoId)
}
