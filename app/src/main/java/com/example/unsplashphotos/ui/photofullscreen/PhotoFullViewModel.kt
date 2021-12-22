package com.example.unsplashphotos.ui.photofullscreen


import androidx.lifecycle.ViewModel
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase
import com.example.unsplashphotos.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoFullViewModel @Inject constructor(private val photoFullScreenUseCase: PhotoFullScreenUseCase) :
    ViewModel() {

    private val mutableStateFlow = MutableStateFlow<Resource<Photo>?>(Resource.loading(null))
    val stateFlow = mutableStateFlow.asStateFlow()

    suspend fun getPhotoById(id: String) {
        mutableStateFlow.value =photoFullScreenUseCase.getSinglePhoto(id)
    }
}
