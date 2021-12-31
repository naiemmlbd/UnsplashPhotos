package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.unsplashphotos.data.api.PhotoRemoteDataSource
import com.example.unsplashphotos.data.model.EntityMapperImpl
import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.data.repository.PhotoPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    photoRemote: PhotoRemoteDataSource,
    mapper: EntityMapperImpl
) : ViewModel() {

    val photos: Flow<PagingData<Photo>> = Pager(
        PagingConfig(pageSize = PhotoPagingSource.PAGE_SIZE, enablePlaceholders = false)
    ) {
        PhotoPagingSource(photoRemote, mapper)
    }.flow
}
