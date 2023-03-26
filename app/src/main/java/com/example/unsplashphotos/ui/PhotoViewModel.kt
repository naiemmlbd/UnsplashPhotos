package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.unsplashphotos.data.repository.PhotoPagingSource
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val fetchPhotoUseCase: FetchPhotoUseCase,
) : ViewModel() {

    fun getPhotos() = Pager(
        PagingConfig(pageSize = PhotoPagingSource.PAGE_SIZE, enablePlaceholders = false),
    ) {
        PhotoPagingSource(fetchPhotoUseCase = fetchPhotoUseCase)
    }.flow.cachedIn(viewModelScope)
}
