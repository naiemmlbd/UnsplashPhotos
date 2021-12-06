package com.example.unsplashphotos.data.repository

import android.util.Log
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource
) : PhotoRepo {
    override suspend fun getPhotos(page: Int): List<Photo>? {
        return getPhotosFromAPI(page)
    }

    override suspend fun getPhotoById(photoId: String): Photo {

        var photoStore: Photo? = null

        try {
            val response = photoDataSource.getPhotoById(photoId)
            val body = response.body()
            Log.i("T===>", "Photo: " + body)

            if (body != null) {
                photoStore = body
            }

        } catch (exception: Exception) {
            Log.e("ErrorPhoto", exception.message.toString())
        }

        return photoStore!!
    }


    private suspend fun getPhotosFromAPI(page: Int): List<Photo> {
        var photoList = listOf<Photo>()

        try {
            val response = photoDataSource.getPhotos(page)
            val body = response.body()
            Log.d("===>", "CheckPoint 1" + body);

            if (body != null) {
                photoList = body
            }

        } catch (exception: Exception) {
            Log.e("ERROR", exception.message.toString())
        }


        Log.d("===>", "CheckPoint 2" + photoList);
        return photoList
    }

}