package com.example.unsplashphotos.data.repository

import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import timber.log.Timber
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDataSource: PhotoDataSource,
    private val photoCacheDataSource: PhotoCacheDataSource
) : PhotoRepo {
    override suspend fun getPhotos(page: Int): List<Photo>? {
        return getPhotosFromAPI(page)
    }

    override suspend fun getPhotoById(photoId: String): Photo? {

        var photoStore: Photo? = null

        try {
            val response = photoDataSource.getPhotoById(photoId)
            val body = response.body()
            Timber.tag("T===>").i("Photo: %s", body)

            if (body != null) {
                photoStore = body
            }

        } catch (exception: Exception) {
            Timber.e(exception.message.toString())
        }

        return photoStore
    }

    suspend fun getPhotosFromCache(page: Int): List<Photo> {
        var photoList: List<Photo> = ArrayList<Photo>()

        if (photoCacheDataSource.getPhotoFromCache(page) == null) {
            photoList = getPhotosFromAPI(page)
            photoCacheDataSource.savePhotoToCache(page, photoList)
        }else{
            photoList = photoCacheDataSource.getPhotoFromCache(page)!!
        }

        return photoList
    }

    private suspend fun getPhotosFromAPI(page: Int): List<Photo> {
        var photoList = listOf<Photo>()

        try {
            val response = photoDataSource.getPhotos(page)
            val body = response.body()
            Timber.tag("===>").d("CheckPoint 1%s", body);

            if (body != null) {
                photoList = body
            }

        } catch (exception: Exception) {
            Timber.e(exception.message.toString())
        }


        Timber.tag("===>").d("CheckPoint 2%s", photoList);
        return photoList
    }

}