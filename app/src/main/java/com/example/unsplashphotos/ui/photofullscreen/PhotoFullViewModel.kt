package com.example.unsplashphotos.ui.photofullscreen


import androidx.lifecycle.ViewModel
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.domain.usecase.PhotoFullScreenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PhotoFullViewModel @Inject constructor(private val photoFullScreenUseCase: PhotoFullScreenUseCase) :
    ViewModel() {

    lateinit var flow: Flow<Photo?>

    suspend fun getPhotoById(id: String) {
        flow = flow {
            val photo = photoFullScreenUseCase.execute(id)
            Timber.tag("===>").i("Photo: %s", photo)
            emit(photo)
        }
    }

}
