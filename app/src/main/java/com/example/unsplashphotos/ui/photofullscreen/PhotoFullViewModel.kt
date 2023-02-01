package com.example.unsplashphotos.ui.photofullscreen


import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unsplashphotos.data.repository.DownloaderUtils
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.domain.usecase.FetchPhotoFullScreenUseCase
import com.example.unsplashphotos.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoFullViewModel @Inject constructor(
    private val downloaderUtils: DownloaderUtils,
    private val fetchPhotoFullScreenUseCase: FetchPhotoFullScreenUseCase
) : ViewModel(), Observable {

    var bitmapDrawable: MutableState<BitmapDrawable?> = mutableStateOf(null)
    private val viewModelState: MutableStateFlow<DataState<Photo>?> =
        MutableStateFlow(DataState.Loading())

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value
        )

    @Bindable
    val fabToggle = MutableLiveData<Boolean>()
    private val fabMutableStateFlow = MutableStateFlow<Boolean>(false)
    val fabStateFlow = fabMutableStateFlow.asStateFlow()


    fun getPhotoById(photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            viewModelState.value = fetchPhotoFullScreenUseCase.getPhoto(photoId)
        }
    }

    fun onClickDownloadFab(url: String, photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (photoId.isNotEmpty())
                downloaderUtils.downloadPhoto(url, photoId)
        }
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

    override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {
    }
}
