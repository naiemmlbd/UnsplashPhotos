package com.example.unsplashphotos.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.unsplashphotos.TestCoroutineRule
import com.example.unsplashphotos.data.repository.PhotoDataSource
import com.example.unsplashphotos.domain.usecase.FetchPhotoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PhotoViewModelTest{

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var photoDataSource: PhotoDataSource

    @Mock
    private lateinit var photoUseCase: FetchPhotoUseCase


}
