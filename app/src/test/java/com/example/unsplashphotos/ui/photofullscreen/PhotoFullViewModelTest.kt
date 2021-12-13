package com.example.unsplashphotos.ui.photofullscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unsplashphotos.utils.Status
import com.example.unsplashphotos.TestCoroutineRule
import com.example.unsplashphotos.getPhotoList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PhotoFullViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var photoFullUseCase: FakePhotoFullUseCase
    private lateinit var photoFullViewModel: PhotoFullViewModel

    @Before
    fun setUp() {
        photoFullUseCase = FakePhotoFullUseCase(getPhotoList())
        photoFullViewModel = PhotoFullViewModel(photoFullUseCase)
    }

    @Test
    fun getPhotoFull_success() = testCoroutineRule.runBlockingTest {
        photoFullViewModel.getPhotoById("aa")

        var photoFullData = photoFullViewModel.flow.first()
        assertThat(photoFullData.status,equalTo(Status.SUCCESS))
        assertThat(photoFullData.data?.id,equalTo("aa"))
    }

    @Test
    fun getPhotoFull_error() = testCoroutineRule.runBlockingTest{
        photoFullUseCase.setReturnError(true)
        photoFullViewModel.getPhotoById("aa")
        var photoFullData = photoFullViewModel.flow.first()
        assertThat(photoFullData.status,equalTo(Status.ERROR))
    }
}
