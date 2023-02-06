package com.example.unsplashphotos.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest.Builder
import com.example.unsplashphotos.domain.model.Photo
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.flow.Flow

@Composable
private fun PhotoGriding(
    innerPadding: PaddingValues,
    photos: Flow<PagingData<Photo>>,
    onPhotoClick: (Photo) -> Unit
) {
    val photos = photos.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(180.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = innerPadding,
        state = rememberLazyGridState()
    ) {
        items(photos.itemCount, key = { it }) { index ->
            photos[index]?.let {
                PhotoItemCard(photo = it, onClickPhoto = onPhotoClick)
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
    message: String
) {
    item {
        Text(
            text = message,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.error
        )
    }
}

@Composable
fun GalleryScreen(
    viewModel: PhotoViewModel,
    modifier: Modifier = Modifier,
    onPhotoClick: (Photo) -> Unit,
) {
    Scaffold(
    ) { innerPadding ->
        PhotoGriding(innerPadding, remember { viewModel.photos }, onPhotoClick)
    }
}


@Composable
fun PhotoItemCard(
    photo: Photo,
    onClickPhoto: (Photo) -> Unit = {}
) {
    Card(
        elevation = 20.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickPhoto(photo) },
        shape = RoundedCornerShape(0.dp)
    ) {
        AsyncImage(
            model = Builder(LocalContext.current).data(photo.urls.thumb).crossfade(true)
                .build(),
            contentDescription = "contentDescription",
            modifier = Modifier
                .placeholder(
                    visible = photo.urls.thumb.isEmpty(),
                    highlight = PlaceholderHighlight.shimmer(),
                )
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}
