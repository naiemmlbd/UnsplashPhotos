package com.example.unsplashphotos.di

import com.example.unsplashphotos.data.BASE_URL
import com.example.unsplashphotos.data.RetrofitInstance
import com.example.unsplashphotos.data.api.PhotoService
import com.example.unsplashphotos.data.repository.PhotoDataSource
import com.example.unsplashphotos.data.repository.PhotoDataSourceImpl
import com.example.unsplashphotos.data.repository.PhotoRepoImpl
import com.example.unsplashphotos.domain.repository.PhotoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): PhotoService {
        return RetrofitInstance.getRetrofitInstance(BASE_URL).create(PhotoService::class.java)
    }

    @Singleton
    @Provides
    fun providePhotoDataSource(): PhotoDataSource = PhotoDataSourceImpl(provideRetrofitInstance())

    @Singleton
    @Provides
    fun providePhotoRepo(): PhotoRepo = PhotoRepoImpl(providePhotoDataSource())

}