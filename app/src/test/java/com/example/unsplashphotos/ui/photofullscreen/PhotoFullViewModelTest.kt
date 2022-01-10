package com.example.unsplashphotos.ui.photofullscreen

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.platform.app.InstrumentationRegistry
import com.example.unsplashphotos.TestCoroutineRule
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.getPhotoList
import com.example.unsplashphotos.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoFullViewModelTest(
){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var photoFullUseCase: FakePhotoFullUseCase
    private lateinit var photoFullViewModel: PhotoFullViewModel
    private lateinit var state: SavedStateHandle
    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        photoFullUseCase = FakePhotoFullUseCase(getPhotoList())
        state = SavedStateHandle()
        state.set("photoId","aa")
        photoFullViewModel = PhotoFullViewModel(context,photoFullUseCase,state)
    }

    @Test
    fun getPhotoFull_success() = testCoroutineRule.runBlockingTest {
        photoFullViewModel.getPhotoById()

        val photoFullData = photoFullViewModel.stateFlow.first()
        assertThat((photoFullData as DataState.Success).data.id, equalTo("aa"))
    }

    @Test
    fun getPhotoFull_error() = testCoroutineRule.runBlockingTest{
        photoFullUseCase.setReturnError(true)
        photoFullViewModel.getPhotoById()
        val photoFullData = photoFullViewModel.stateFlow.first()
        assertThat((photoFullData is DataState.Error), equalTo(true))
    }
}
