package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.EntityMapperImpl
import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import com.example.unsplashphotos.data.model.domain.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.utils.DataState
import timber.log.Timber
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val entityMapper: EntityMapperImpl
) : PhotoRepo {

    override suspend fun getPhotos(page: Int, perPage: Int): DataState<List<Photo>> {
        return getPhotosFromAPI(page, perPage)
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

    private suspend fun getPhotosFromAPI(page: Int, perPage: Int): DataState<List<Photo>> {
        var photoList = listOf<Photo>()

        val response = photoDataSource.getPhotos(page, perPage)
        if (!response.isSuccessful) {
            return DataState.error(response.message() ?: "Unknown Error")
        }
        val body = response.body()
        if (body != null) {
            photoList = entityMapper.mapFromEntityList(body)
        }

        Timber.tag("===>").d("CheckPoint: %s", photoList)
        return DataState.success(photoList)
    }
}
