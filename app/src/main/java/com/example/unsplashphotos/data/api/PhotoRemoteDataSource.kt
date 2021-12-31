package com.example.unsplashphotos.data.api

import com.example.unsplashphotos.data.model.PhotoRemoteEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PhotoRemoteDataSource {

    @GET("/photos/")
    suspend fun getPhotos(@Query("client_id") clientId: String,@Query("page") page: Int): Response<List<PhotoRemoteEntity>>

    @GET("/photos/{id}")
    suspend fun getPhotoById(@Path(value = "id") id: String, @Query("client_id") clientId: String): Response<PhotoRemoteEntity>
}
