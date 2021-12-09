package com.example.unsplashphotos.di

import android.content.Context
import com.example.unsplashphotos.common.ImageLoader
import com.example.unsplashphotos.data.repository.DownloaderUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader{
        return ImageLoader(context)
    }

    @Provides
    @Singleton
    fun provideDownloader(@ApplicationContext context: Context): DownloaderUtils{
        return DownloaderUtils(context)
    }
}
