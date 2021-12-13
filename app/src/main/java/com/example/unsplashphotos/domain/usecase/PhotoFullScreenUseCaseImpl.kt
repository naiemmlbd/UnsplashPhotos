package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.utils.Resource
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject


class PhotoFullScreenUseCaseImpl @Inject constructor(private val photoRepo: PhotoRepo): PhotoFullScreenUseCase {

    override suspend fun getSinglePhoto(id: String): Resource<Photo> = photoRepo.getPhotoById(id)
}
