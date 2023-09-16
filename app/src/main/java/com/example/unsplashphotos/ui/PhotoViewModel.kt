package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.unsplashphotos.data.repository.PhotoPagingSource
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val fetchPhotoUseCase: FetchPhotoUseCase,
) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    fun getPhotos() = Pager(
        PagingConfig(pageSize = PhotoPagingSource.PAGE_SIZE, enablePlaceholders = false),
    ) {
        PhotoPagingSource(fetchPhotoUseCase = fetchPhotoUseCase)
    }.flow.onStart { _isLoading.emit(false) }
        .cachedIn(viewModelScope)
}
