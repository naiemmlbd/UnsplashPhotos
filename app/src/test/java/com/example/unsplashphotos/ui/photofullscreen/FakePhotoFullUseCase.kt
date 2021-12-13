package com.example.unsplashphotos.ui.photofullscreen

import com.example.unsplashphotos.utils.Resource
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase


class FakePhotoFullUseCase(private val singlePhotoList: MutableList<Photo>): PhotoFullScreenUseCase{
    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getSinglePhoto(photoId: String): Resource<Photo> {
        if(shouldReturnError) {
            return Resource.error("Test Exception")
        }
        val photoFull = singlePhotoList.find { it.id == photoId } ?: return Resource.error("Not found")
        return Resource.success(photoFull)
    }
}
