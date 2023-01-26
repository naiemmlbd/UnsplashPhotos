package com.example.unsplashphotos.ui.photofullscreen

import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.unsplashphotos.R
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.AppBar
import com.example.unsplashphotos.ui.PhotoItem
import com.example.unsplashphotos.ui.theme.UnsplashTheme
import com.example.unsplashphotos.utils.DataState.Error
import com.example.unsplashphotos.utils.DataState.Loading
import com.example.unsplashphotos.utils.DataState.Success

@Composable
fun PhotoFullScreen(modifier: Modifier = Modifier, onShareClicked: () -> Unit, photo: Photo) {
    val viewModel = hiltViewModel<PhotoFullViewModel>()
    UnsplashTheme {
        Scaffold(
            topBar = {
                AppBar(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                )
            },
            floatingActionButton = {
                Column() {
                    FloatingActionButtonShare(onShareClicked = onShareClicked)
                    Spacer(modifier = Modifier.height(8.dp))
                    FloatingActionButtonDownload()
                }
            }
        ) { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                val drawable = PhotoItem(
                    photoUrl = photo.urls.regular,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                drawable?.let {
                    viewModel.bitmapDrawable.value = it as BitmapDrawable
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 20.dp, start = 5.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom,
            ) {
                FloatingActionButtonInfo()
            }
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun PhotoFullScreen(photoId: String, onShareClicked: (BitmapDrawable?) -> Unit) {
    val viewModel = hiltViewModel<PhotoFullViewModel>()
    viewModel.getPhotoById(photoId)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is Error -> {}
        is Loading -> {
            FullScreenLoading()
        }
        is Success -> {
            PhotoFullScreen(
                Modifier,
                onShareClicked = { onShareClicked(viewModel.bitmapDrawable.value) },
                (uiState as Success<Photo>).data
            )
        }
        else -> {}
    }

}


@Composable
fun FloatingActionButtonShare(
    onShareClicked: () -> Unit = {},
    containerColor: Color = Color.Cyan,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp)
) {
    FloatingActionButton(
        onClick = onShareClicked,
        shape = shape,
        backgroundColor = containerColor
    ) {
        Icon(
            imageVector = Rounded.Share,
            contentDescription = "Share photo",
            tint = Color.Black,
        )
    }
}

@Composable
fun FloatingActionButtonInfo(
    onClick: () -> Unit = {},
    containerColor: Color = Color.Cyan,
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50))
) {
    FloatingActionButton(
        modifier = Modifier.size(40.dp),
        onClick = onClick,
        shape = shape,
        backgroundColor = containerColor
    ) {
        Icon(
            imageVector = Rounded.Info,
            contentDescription = "Information",
            tint = Color.Black,
        )
    }
}

@Composable
fun FloatingActionButtonDownload(
    onClick: () -> Unit = {},
    containerColor: Color = Color.Cyan,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
    FloatingActionButton(onClick = onClick, shape = shape, backgroundColor = containerColor) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_arrow_circle_down_24),
            contentDescription = "Download",
            tint = Color.Black,
        )
    }
}

@Preview("Photo item")
@Composable
private fun PhotoFullScreenPreview() {
    UnsplashTheme {
//        PhotoFullScreen(Modifier, Photo("", "", Urls.EMPTY, Links.EMPTY, 0))
    }
}
