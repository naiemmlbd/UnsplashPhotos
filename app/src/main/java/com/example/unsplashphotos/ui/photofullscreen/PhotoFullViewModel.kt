package com.example.unsplashphotos.ui.photofullscreen


import android.content.Context
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.unsplashphotos.data.repository.DownloaderUtils.downloadPhoto
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.usecase.FetchPhotoFullScreenUseCase
import com.example.unsplashphotos.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoFullViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val fetchPhotoFullScreenUseCase: FetchPhotoFullScreenUseCase,
    state: SavedStateHandle
) : ViewModel(), Observable {

    val photoId = state.get<String>("photoId")
    private val mutableStateFlow: MutableStateFlow<DataState<Photo>?> = MutableStateFlow(DataState.Loading())
    val stateFlow = mutableStateFlow.asStateFlow()
    @Bindable
    val fabToggle = MutableLiveData<Boolean>()
    private val fabMutableStateFlow = MutableStateFlow<Boolean>(false)
    val fabStateFlow = fabMutableStateFlow.asStateFlow()


    suspend fun getPhotoById() {
        if (photoId != null){
            mutableStateFlow.value = fetchPhotoFullScreenUseCase.getPhoto(photoId)
        }
    }

    fun onClickDownloadFab(url: String) {
        if (photoId != null)
            downloadPhoto(context, url, photoId)
    }

    fun onImageClicked() {
        if (fabToggle.value == true) {
            fabToggle.value = false
            fabMutableStateFlow.value = false
        } else {
            fabToggle.value = true
            fabMutableStateFlow.value = true
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
