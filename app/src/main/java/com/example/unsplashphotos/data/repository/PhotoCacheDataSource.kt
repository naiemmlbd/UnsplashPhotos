package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.Photo


interface PhotoCacheDataSource {

    suspend fun getPhotoFromCache(page: Int):List<Photo>?
    suspend fun savePhotoToCache(page: Int, photos:List<Photo>)
}
