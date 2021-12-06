package com.example.unsplashphotos.ui.photofullscreen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class PhotoFullViewModel @Inject constructor(private val photoFullScreenUseCase: PhotoFullScreenUseCase) :
    ViewModel() {

    fun getPhotoById(id: String) = liveData{
        val photo = photoFullScreenUseCase.execute(id)
        Log.i("===>","Photo::::"+ photo)
        emit(photo)
    }


}