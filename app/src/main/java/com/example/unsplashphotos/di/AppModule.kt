package com.example.unsplashphotos.di

import android.app.Application
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
class AppModule {

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader(context)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.baseContext
    }

    @Provides
    @Singleton
    fun provideDownloaderUtils(context: Context): DownloaderUtils {
        return DownloaderUtils(context = context)
    }
}
