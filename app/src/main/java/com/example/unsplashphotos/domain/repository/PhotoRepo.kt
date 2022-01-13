package com.example.unsplashphotos.domain.repository

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.utils.DataState


interface PhotoRepo {
    suspend fun getPhotos(page: Int, perPage: Int): DataState<List<Photo>>
    suspend fun getPhotoById(photoId: String): DataState<Photo>
}
