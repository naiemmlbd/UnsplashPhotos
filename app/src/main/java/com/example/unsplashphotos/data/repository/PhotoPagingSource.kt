package com.example.unsplashphotos.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.unsplashphotos.BuildConfig
import com.example.unsplashphotos.data.api.PhotoRemoteDataSource
import com.example.unsplashphotos.data.model.EntityMapperImpl
import com.example.unsplashphotos.data.model.local.Photo

class PhotoPagingSource(
    private val photoRemote: PhotoRemoteDataSource,
    private val mapper: EntityMapperImpl
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE
        val perPage = params.loadSize
        val photoResult = photoRemote.getPhotos(BuildConfig.CLIENT_ID, page, perPage)
        if (photoResult.isSuccessful) {
            val list = photoResult.body()
            val data = list?.map { mapper.mapFromEntity(it) }.orEmpty()
            val nextPage = if (data.isEmpty()) null else page + (params.loadSize / PAGE_SIZE)
            return LoadResult.Page(
                data = data,
                prevKey = if (page == STARTING_PAGE) null else page - 1,
                nextKey = nextPage
            )
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
