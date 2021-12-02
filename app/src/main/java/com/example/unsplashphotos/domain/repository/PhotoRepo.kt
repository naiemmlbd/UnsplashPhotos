package com.example.unsplashphotos.domain.repository

import com.example.unsplashphotos.data.model.Photo


interface PhotoRepo {
    suspend fun getPhotos(): List<Photo>?
    suspend fun getPhotoById(photoId: String): Photo


}