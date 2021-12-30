package com.example.unsplashphotos.domain.usecase


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.unsplashphotos.data.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FetchPhotoUseCase @Inject constructor(private val photoPagingSource: PagingSource<Int, Photo>) {

    private val config = PagingConfig(pageSize = 20, enablePlaceholders = false)

    fun fetchPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config
        ) {
            photoPagingSource
        }.flow
    }
}
