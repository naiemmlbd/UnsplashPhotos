package com.example.unsplashphotos.di

import com.example.unsplashphotos.domain.repository.PhotoRepo
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
        photoRepo: PhotoRepo
    ): PhotoUseCase = PhotoUseCase(photoRepo)


}