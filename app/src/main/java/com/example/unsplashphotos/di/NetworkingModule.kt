package com.example.unsplashphotos.di

import com.example.unsplashphotos.BuildConfig.BASE_URL
import com.example.unsplashphotos.BuildConfig.CLIENT_ID
import com.example.unsplashphotos.data.api.PhotoRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideNetworkInterceptor(): Interceptor = Interceptor {
        val original: Request = it.request()
        val url = original.url.newBuilder().addQueryParameter("client_id", CLIENT_ID)
            .build()
        val requestBuilder = original.newBuilder().url(url)
        val request = requestBuilder.build()
        return@Interceptor it.proceed(request)
    }

    @Provides
    fun provideClient(networkInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(interceptor).build()

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun getRetrofitInstance(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): PhotoRemoteDataSource =
        retrofit.create(PhotoRemoteDataSource::class.java)
}
