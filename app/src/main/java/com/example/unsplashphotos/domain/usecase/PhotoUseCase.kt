package com.example.unsplashphotos.domain.usecase


import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo


class PhotoUseCase(private val photoRepo: PhotoRepo) {

    suspend fun getPhotos(): List<Photo>? = photoRepo.getPhotos()

    suspend fun getPhotoByID(photoId: String): Photo = photoRepo.getPhotoById(photoId)
}