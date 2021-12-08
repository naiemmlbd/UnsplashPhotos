package com.example.unsplashphotos.di

import com.example.unsplashphotos.data.repository.PhotoDataSource
import com.example.unsplashphotos.data.repository.PhotoDataSourceImpl
import com.example.unsplashphotos.data.repository.PhotoRepoImpl
import com.example.unsplashphotos.domain.repository.PhotoRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhotoDataSource(photoDataSourceImpl: PhotoDataSourceImpl): PhotoDataSource

    @Singleton
    @Binds
    abstract fun getPhotoRepository(photoRepoImpl: PhotoRepoImpl): PhotoRepo

}
