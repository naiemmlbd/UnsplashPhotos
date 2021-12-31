package com.example.unsplashphotos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.data.repository.PhotoPagingSource
import com.example.unsplashphotos.domain.repository.PhotoRepo
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(fetchPhotoUseCase: FetchPhotoUseCase, photoRepo: PhotoRepo) : ViewModel() {

    val photos: Flow<PagingData<Photo>> = fetchPhotoUseCase.fetchPhotos().cachedIn(viewModelScope)
//
//    val photoPagingData = Pager(
//        PagingConfig(pageSize = 10, enablePlaceholders = false)
//    ){
//        PhotoPagingSource(photoRepo)
//    }.flow
}
