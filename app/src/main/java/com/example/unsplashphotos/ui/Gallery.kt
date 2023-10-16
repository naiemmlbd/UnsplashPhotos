package com.example.unsplashphotos.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.example.unsplashphotos.R
import com.example.unsplashphotos.R.string
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.destinations.PhotoFullScreenDestination
import com.example.unsplashphotos.ui.theme.Bittersweet
import com.example.unsplashphotos.ui.theme.UnsplashTheme
import com.example.unsplashphotos.ui.theme.UnsplashTypography
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.Flow

@Composable
private fun PhotoGriding(
    innerPadding: PaddingValues,
    photos: Flow<PagingData<Photo>>,
    onPhotoClick: (Photo) -> Unit,
) {
    val photos = photos.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = innerPadding,
        state = rememberLazyGridState(),
    ) {
        when (val state = photos.loadState.prepend) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
        items(photos.itemCount, key = { it }) { index ->
            photos[index]?.let {
                PhotoItemCard(photo = it, onClickPhoto = onPhotoClick)
            }
        }
        when (val state = photos.loadState.append) {
            is LoadState.NotLoading -> Unit
            is LoadState.Loading -> {
                Loading()
            }

            is LoadState.Error -> {
                Error(message = state.error.message ?: "")
            }
        }
    }
}

private fun LazyGridScope.Loading() {
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(2) }
    item(span = span) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

private fun LazyGridScope.Error(
    message: String,
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error,
        )
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun GalleryScreen(
    navigator: DestinationsNavigator,
) {
    val photoViewModel: PhotoViewModel = hiltViewModel()
    val photos = photoViewModel.getPhotos()
    val isLoading = photoViewModel.isLoading.collectAsStateWithLifecycle(true).value

    photoGallery(photos, navigator, isLoading)
}

@Composable
private fun photoGallery(
    photos: Flow<PagingData<Photo>>,
    navigator: DestinationsNavigator,
    isLoading: Boolean
) {
    UnsplashTheme {
        Scaffold(
            topBar = {
                AppBar(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth(),
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 0.dp, vertical = 6.dp)
            ) {
                PhotoGriding(innerPadding, remember { photos }, onPhotoClick = { photo ->
                    navigator.navigate(
                        PhotoFullScreenDestination(
                            photo = photo,
                        ),
                    )
                })
                if (isLoading) {
                    ProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun PhotoItemCard(
    photo: Photo,
    onClickPhoto: (Photo) -> Unit = {},
) {
    Card(
        elevation = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickPhoto(photo) },
        shape = RoundedCornerShape(0.dp),
    ) {
        ConstraintLayout {
            val (image, title) = createRefs()
            AsyncImage(
                model = Builder(LocalContext.current).data(photo.urls.thumb).crossfade(true)
                    .build(),
                contentDescription = "contentDescription",
                modifier = Modifier
                    .placeholder(
                        visible = photo.urls.thumb.isEmpty(),
                        highlight = PlaceholderHighlight.shimmer(),
                    )
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )
            Text(
                text = photo.altDescription.ifEmpty { "Unsplash" },
                style = UnsplashTypography.body1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }
    }
}


@Composable
fun AppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    Bittersweet,
                    Bittersweet,
                ),
            ),
        ),
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_local_florist_24),
                contentDescription = null,
                modifier = Modifier.padding(start = 12.dp),
            )
        },
        title = {
            Text(text = stringResource(string.app_name))
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
}
