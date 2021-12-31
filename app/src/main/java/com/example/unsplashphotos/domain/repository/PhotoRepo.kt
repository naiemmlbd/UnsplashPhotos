package com.example.unsplashphotos.domain.repository

import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import com.example.unsplashphotos.data.model.local.Photo


interface PhotoRepo {
    suspend fun getPhotos(page: Int): List<Photo>?
    suspend fun getPhotoById(photoId: String): PhotoRemoteEntity?
}
