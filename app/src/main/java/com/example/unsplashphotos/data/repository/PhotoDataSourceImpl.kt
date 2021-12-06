package com.example.unsplashphotos.data.repository

import android.util.Log
import com.example.unsplashphotos.data.CLIENT_ID
import com.example.unsplashphotos.data.api.PhotoService
import com.example.unsplashphotos.data.model.Photo
import retrofit2.Response
import javax.inject.Inject


class PhotoDataSourceImpl @Inject constructor(
    private val photoService: PhotoService
) : PhotoDataSource {

    override suspend fun getPhotos(page: Int): Response<List<Photo>> {
        val store = photoService.getPhotos(CLIENT_ID, page)
        Log.d("===>", "photos: " + store)
        return store
    }

    override suspend fun getPhotoById(photoId: String): Response<Photo> {

        val photo = photoService.getPhotoById(photoId, CLIENT_ID)

        Log.d("===>", "CheckPhoto: " + photo)

        return photo
    }

}