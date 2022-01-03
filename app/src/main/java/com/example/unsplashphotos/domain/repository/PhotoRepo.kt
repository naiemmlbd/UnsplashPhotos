package com.example.unsplashphotos.domain.repository

import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.utils.DataState


interface PhotoRepo {
    suspend fun getPhotos(page: Int, perPage: Int): DataState<List<Photo>>
    suspend fun getPhotoById(photoId: String): PhotoRemoteEntity?
}
