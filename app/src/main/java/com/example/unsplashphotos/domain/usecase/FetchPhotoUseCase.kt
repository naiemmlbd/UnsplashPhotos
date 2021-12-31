package com.example.unsplashphotos.domain.usecase


import androidx.paging.PagingConfig
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject


class FetchPhotoUseCase @Inject constructor(val photoRepo: PhotoRepo) {

    private val config = PagingConfig(pageSize = 10, enablePlaceholders = false)

//    fun fetchPhotos(): Flow<PagingData<Photo>> {
//        return Pager(
//            config
//        ) {
//            PhotoPagingSource(photoRepo)
//        }.flow
//    }
}
