package com.example.unsplashphotos.ui.photofullscreen

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult.ActionPerformed
import androidx.compose.material.icons.Icons.Rounded
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.unsplashphotos.R
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.AppBar
import com.example.unsplashphotos.ui.photoItem
import com.example.unsplashphotos.ui.theme.UnsplashTheme
import com.example.unsplashphotos.utils.DataState.Error
import com.example.unsplashphotos.utils.DataState.Loading
import com.example.unsplashphotos.utils.DataState.Success
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoFullScreen(modifier: Modifier = Modifier, onShareClicked: () -> Unit, photo: Photo) {
    val context = LocalContext.current.applicationContext
    val viewModel = hiltViewModel<PhotoFullViewModel>()
    val requiredPermissionsState =
        rememberPermissionState(permission = permission.WRITE_EXTERNAL_STORAGE)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val permissionRequested = remember { mutableStateOf(false) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val permissionObserver = LifecycleEventObserver { _, event ->
            if (event == ON_RESUME && requiredPermissionsState.permissionRequested) {
                if (requiredPermissionsState.hasPermission && permissionRequested.value) {
                    viewModel.onClickDownloadFab(photo.links.download)
                } else if (permissionRequested.value) {
                    Toast.makeText(
                        context,
                        "Please allow the permission to download the image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                permissionRequested.value = false
            }
        }
        lifecycleOwner.lifecycle.addObserver(permissionObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(permissionObserver)
        }
    }

    UnsplashTheme {
        val snackbarHostState = scaffoldState.snackbarHostState
        Scaffold(topBar = {
            AppBar(
                Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            )
        }, scaffoldState = scaffoldState, floatingActionButton = {
            FloatingActionButtons(
                onShareClicked,
                requiredPermissionsState,
                viewModel,
                photo,
                scope,
                snackbarHostState,
                context,
                permissionRequested
            )
        }) { innerPadding ->
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding), contentAlignment = Alignment.Center
            ) {
                val drawable = photoItem(
                    photoUrl = photo.urls.regular,
                    modifier = Modifier.fillMaxSize(),
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
                FloatingActionButtonInfo(onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Likes: ${photo.likes}", actionLabel = null
                        )
                    }
                })
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun FloatingActionButtons(
    onShareClicked: () -> Unit,
    requiredPermissionsState: PermissionState,
    viewModel: PhotoFullViewModel,
    photo: Photo,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    context: Context,
    permissionRequested: MutableState<Boolean>
) {
    Column() {
        FloatingActionButtonShare(onShareClicked = onShareClicked)
        Spacer(modifier = Modifier.height(8.dp))
        FloatingActionButtonDownload(onDownloadClicked = {
            if (requiredPermissionsState.hasPermission) {
                viewModel.onClickDownloadFab(photo.links.download)
            } else if (requiredPermissionsState.shouldShowRationale) {
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Permission required", actionLabel = "Go to settings"
                    )
                    if (result == ActionPerformed) {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent)
                    }
                }
            } else {
                requiredPermissionsState.launchPermissionRequest()
                permissionRequested.value = true
            }
        })
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
        onClick = onShareClicked, shape = shape, backgroundColor = containerColor
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
    onDownloadClicked: () -> Unit = {},
    containerColor: Color = Color.Cyan,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
) {
    FloatingActionButton(
        onClick = onDownloadClicked, shape = shape, backgroundColor = containerColor
    ) {
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
