package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashphotos.data.model.domain.Photo
import com.example.unsplashphotos.data.repository.PhotoPagingSource
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    fetchPhotoUseCase: FetchPhotoUseCase
) : ViewModel() {

    val photos: Flow<PagingData<Photo>> = Pager(
        PagingConfig(pageSize = PhotoPagingSource.PAGE_SIZE, enablePlaceholders = false)
    ) {
        PhotoPagingSource(fetchPhotoUseCase)
    }.flow.cachedIn(viewModelScope)
}
