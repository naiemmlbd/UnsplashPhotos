package com.example.unsplashphotos.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilityModule {

//    @Binds
//    @Singleton
//    fun getPagingSource(photoPagingSource: PhotoPagingSource): PagingSource<Int, Photo>
}
