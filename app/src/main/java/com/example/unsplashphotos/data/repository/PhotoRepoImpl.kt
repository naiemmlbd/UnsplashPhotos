package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.EntityMapper
import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import timber.log.Timber
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val entityMapper: EntityMapper
    ) : PhotoRepo {

    override suspend fun getPhotos(page: Int): List<Photo>? {
        return getPhotosFromAPI(page)
    }

    override suspend fun getPhotoById(photoId: String): PhotoRemoteEntity? {
        var photoRemoteEntityStore: PhotoRemoteEntity? = null
        try {
            val response = photoDataSource.getPhotoById(photoId)
            val body = response.body()
            Timber.tag("T===>").i("Photo: %s", body)
            if (body != null) {
                photoRemoteEntityStore = body
            }
        } catch (exception: Exception) {
            Timber.e(exception.message.toString())
        }
        return photoRemoteEntityStore
    }

    private suspend fun getPhotosFromAPI(page: Int): List<Photo> {
        var photoList = listOf<Photo>()
        try {
            val response = photoDataSource.getPhotos(page)
            if (response.isSuccessful){
                val body = response.body()
                if (body != null) {
                    photoList = entityMapper.mapFromEntityList(body)
                }
            }

        } catch (exception: Exception) {
            Timber.e(exception.message.toString())
        }
        Timber.tag("===>").d("CheckPoint: %s", photoList);
        return photoList
    }
}
