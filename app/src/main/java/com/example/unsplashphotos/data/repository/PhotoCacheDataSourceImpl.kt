package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.Photo
import javax.inject.Inject


class  PhotoCacheDataSourceImpl @Inject constructor () : PhotoCacheDataSource {

    var maxMemory:Int = ((Runtime.getRuntime().maxMemory() / 1024).toInt())
    private val maxCacheSize =maxMemory / 8
    var cache = LruBitmapCache(maxCacheSize)
    override suspend fun getPhotoFromCache(page: Int): List<Photo>? {
        return cache.getBitmap(page)
    }

    override suspend fun savePhotoToCache(page: Int, photos: List<Photo>) {
        if (cache.getBitmap(page) == null){
            cache.putBitmap(page, photos)
        }
    }
}
