package com.example.unsplashphotos.ui.photofullscreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotoFullViewModel @Inject constructor(private val photoFullScreenUseCase: PhotoFullScreenUseCase) :
    ViewModel() {

    fun getPhotoById(id: String) = liveData{
        val photo = photoFullScreenUseCase.execute(id)
        Timber.tag("===>").i("Photo: " + photo)
        emit(photo)
    }

}
