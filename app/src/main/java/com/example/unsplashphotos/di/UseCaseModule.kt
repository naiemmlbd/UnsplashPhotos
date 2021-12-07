package com.example.unsplashphotos.di

import com.example.unsplashphotos.data.repository.PhotoPagingSource
import com.example.unsplashphotos.domain.usecase.PhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun providePhotoUseCase(
        photoPagingSource: PhotoPagingSource
    ): PhotoUseCase = PhotoUseCase(photoPagingSource)

}