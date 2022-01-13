package com.example.unsplashphotos.data.api

import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PhotoRemoteDataSource {

    @GET("/photos/")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<PhotoRemoteEntity>>

    @GET("/photos/{id}")
    suspend fun getPhotoById(
        @Path(value = "id") id: String
    ): Response<PhotoRemoteEntity>
}
