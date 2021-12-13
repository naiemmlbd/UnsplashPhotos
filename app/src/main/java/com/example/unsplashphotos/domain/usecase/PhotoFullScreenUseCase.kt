package com.example.unsplashphotos.domain.usecase

import com.example.unsplashphotos.utils.Resource
import com.example.unsplashphotos.data.model.Photo


interface PhotoFullScreenUseCase {
    suspend fun getSinglePhoto(id: String): Resource<Photo>

}
