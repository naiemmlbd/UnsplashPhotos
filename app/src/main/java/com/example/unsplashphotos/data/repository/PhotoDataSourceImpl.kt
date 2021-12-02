package com.example.unsplashphotos.data.repository

import android.util.Log
import com.example.unsplashphotos.data.CLIENT_ID
import com.example.unsplashphotos.data.api.PhotoService
import com.example.unsplashphotos.data.model.Photo
import retrofit2.Response


class PhotoDataSourceImpl(
    private val photoService: PhotoService
) : PhotoDataSource {

    override suspend fun getPhotos(): Response<List<Photo>> {
        val store = photoService.getPhotos(CLIENT_ID)
        Log.d("T===>", "photos: " + store)
        return store
    }

}