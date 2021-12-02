package com.example.unsplashphotos.data.api

import com.example.unsplashphotos.data.model.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoService {

    @GET("/photos/")
    suspend fun getPhotos(@Query("client_id") client_id: String): Response<List<Photo>>
}