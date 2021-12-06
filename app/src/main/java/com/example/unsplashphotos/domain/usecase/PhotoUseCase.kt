package com.example.unsplashphotos.domain.usecase


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.unsplashphotos.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PhotoUseCase @Inject constructor(private val photoPagingSource: PagingSource<Int, Photo>) {

    fun fetchPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            PagingConfig(20, enablePlaceholders = false)
        ) {
            photoPagingSource
        }.flow
    }
}