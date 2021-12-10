package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.Photo
import javax.inject.Inject


class  PhotoCacheDataSourceImpl @Inject constructor () : PhotoCacheDataSource {

    var cache = LruBitmapCache()
    override suspend fun getPhotoFromCache(page: Int): List<Photo>? {
        return cache.getBitmap(page)
    }

    override suspend fun savePhotoToCache(page: Int, photos: List<Photo>) {
        if (cache.getBitmap(page) == null){
            cache.putBitmap(page, photos)
        }
    }
}
