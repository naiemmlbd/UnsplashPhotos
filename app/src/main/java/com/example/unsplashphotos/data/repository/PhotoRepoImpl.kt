package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.PhotoRemoteToPhotoMapperImpl
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.utils.DataState
import timber.log.Timber
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val photoRemoteToPhotoMapper: PhotoRemoteToPhotoMapperImpl,
) : PhotoRepo {

    override suspend fun getPhotos(page: Int, perPage: Int): DataState<List<Photo>> {
        return getPhotosFromAPI(page, perPage)
    }

    override suspend fun getPhotoById(photoId: String): DataState<Photo> {
        val response = photoDataSource.getPhotoById(photoId)
        val body = response.body()
        Timber.tag("T===>").i("Photo: %s", body)
        return if (body != null) {
            DataState.Success(photoRemoteToPhotoMapper.mapFromEntity(body))
        } else {
            DataState.Error(Exception(response.message()))
        }
    }

    private suspend fun getPhotosFromAPI(page: Int, perPage: Int): DataState<List<Photo>> {
        var photoList = listOf<Photo>()

        val response = photoDataSource.getPhotos(page, perPage)
        if (!response.isSuccessful) {
            return DataState.Error(Exception(response.message() ?: "Unknown error"))
        }
        val body = response.body()
        if (body != null) {
            photoList = body.map { photoRemoteToPhotoMapper.mapFromEntity(it) }
        }

        Timber.tag("===>").d("CheckPoint: %s", photoList)
        return DataState.Success(photoList)
    }
}
