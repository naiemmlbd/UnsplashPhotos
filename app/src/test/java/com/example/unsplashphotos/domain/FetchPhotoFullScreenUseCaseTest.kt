package com.example.unsplashphotos.domain

import com.example.unsplashphotos.data.repository.FetchPhotoFullScreenUseCaseImpl
import com.example.unsplashphotos.data.repository.PhotoRepoImpl
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.getPhotoList
import com.example.unsplashphotos.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class FetchPhotoFullScreenUseCaseTest {

    private lateinit var fetchPhotoFullScreenUseCaseImpl: FetchPhotoFullScreenUseCaseImpl
    private lateinit var photoRepoImpl: PhotoRepoImpl


    @Before
    fun setup(){
        photoRepoImpl = Mockito.mock(PhotoRepoImpl::class.java)
        fetchPhotoFullScreenUseCaseImpl = FetchPhotoFullScreenUseCaseImpl(photoRepoImpl)
    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun shouldReturnSinglePhoto() = runBlockingTest{
//        val photoFull = getPhotoList().find { it.id ==  "aa"}
//
//        Mockito.`when`((photoRepoImpl.getPhotoById("aa") as DataState.Success).data).thenReturn(photoFull) }
//
//       fetchPhotoFullScreenUseCaseImpl.getPhoto("aa")
//
//        Mockito.verify(photoRepoImpl,Mockito.times(1).)
//
//
//    }

}
