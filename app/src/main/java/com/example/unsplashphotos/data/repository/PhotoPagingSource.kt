package com.example.unsplashphotos.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import com.example.unsplashphotos.utils.DataState
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(
    private val fetchPhotoUseCase: FetchPhotoUseCase,
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE
        val perPage = params.loadSize
        val photoResult = fetchPhotoUseCase.fetchPhotos(page, perPage)
        when (photoResult) {
            is DataState.Success -> {
                val nextPage =
                    if (photoResult.data.isEmpty()) null else page + (params.loadSize / PAGE_SIZE)
                return LoadResult.Page(
                    data = photoResult.data,
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = nextPage
                )
            }
        }
        return LoadResult.Error(Exception("Error Occurred"))
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PAGE_SIZE = 10
        const val STARTING_PAGE = 1
    }
}
