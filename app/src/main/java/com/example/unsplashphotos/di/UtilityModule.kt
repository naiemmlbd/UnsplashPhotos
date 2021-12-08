package com.example.unsplashphotos.di

import androidx.paging.PagingSource
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.data.repository.PhotoPagingSource

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilityModule {

    @Binds
    @Singleton
    fun getPagingSource(photoPagingSource: PhotoPagingSource): PagingSource<Int, Photo>
}
