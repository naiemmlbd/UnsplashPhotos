package com.example.unsplashphotos.di

import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface UseCaseModule {
    @Binds
    fun getPhotoFullScreenUseCase(photoFullScreenUseCaseImpl: PhotoFullScreenUseCaseImpl):PhotoFullScreenUseCase
}
