package com.example.unsplashphotos.di

import com.example.unsplashphotos.data.repository.FetchPhotoFullScreenUseCaseImpl
import com.example.unsplashphotos.data.repository.FetchPhotoUseCaseImpl
import com.example.unsplashphotos.domain.usecase.FetchPhotoFullScreenUseCase
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {
    @Singleton
    @Binds
    abstract fun getFetchPhotosUseCase(fetchPhotoUseCaseImpl: FetchPhotoUseCaseImpl): FetchPhotoUseCase

    @Singleton
    @Binds
    abstract fun getFetchPhotoFullScreenUseCase(fetchPhotoFullScreenUseCaseImpl: FetchPhotoFullScreenUseCaseImpl): FetchPhotoFullScreenUseCase
}
