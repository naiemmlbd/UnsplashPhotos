package com.example.unsplashphotos.ui.photofullscreen

import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.usecase.FetchPhotoFullScreenUseCase
import com.example.unsplashphotos.utils.DataState


class FakePhotoFullUseCase(private val singlePhotoList: MutableList<Photo>) :
    FetchPhotoFullScreenUseCase {
    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getPhoto(photoId: String): DataState<Photo> {
        if (shouldReturnError) {
            return DataState.Error(Exception("Test Exception"))
        }
        val photoFull = singlePhotoList.find { it.id == photoId } ?: return DataState.Error(
            Exception("Not found")
        )
        return DataState.Success(photoFull)
    }
}
