package com.example.unsplashphotos.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.repository.PhotoRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoPagingSource @Inject constructor(private val photoRepo: PhotoRepoImpl) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        val page = params.key ?: 1

        val photoResult = photoRepo.getPhotos(page)
        if (photoResult != null) {
            return LoadResult.Page(photoResult, if (page == 1) null else page - 1, page + 1)
        }

        return LoadResult.Error(Exception("Error Occurred"))
    }
}