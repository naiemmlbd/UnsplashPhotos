package com.example.unsplashphotos.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells.Adaptive
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.example.unsplashphotos.R
import com.example.unsplashphotos.R.string
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.theme.UnsplashTheme
import kotlinx.coroutines.flow.Flow

@Composable
private fun PhotoGriding(
    innerPadding: PaddingValues,
    photos: Flow<PagingData<Photo>>,
    onPhotoClick: (Photo) -> Unit
) {
    val photos = photos.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = Adaptive(140.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = innerPadding
    ) {
        items(photos.itemCount) { index ->
            photos[index]?.let {
                PhotoItemCard(photo = it, onClickPhoto = onPhotoClick)
            }
        }
    }
}

@Composable
fun GalleryScreen(
    modifier: Modifier = Modifier,
    onPhotoClick: (Photo) -> Unit,
    photos: Flow<PagingData<Photo>>
) {
    UnsplashTheme {
        Scaffold(
            topBar = {
                AppBar(
                    Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                )
            }
        ) { innerPadding ->
            PhotoGriding(innerPadding, photos, onPhotoClick)
        }
    }
}


@Composable
fun PhotoItemCard(
    photo: Photo,
    onClickPhoto: (Photo) -> Unit = {}
) {
    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickPhoto(photo) },
        shape = RoundedCornerShape(0.dp)
    ) {
        photoItem(
            photoUrl = photo.urls.thumb,
            modifier = Modifier
                .height(120.dp)
                .width(60.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun photoItem(
    photoUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
): Drawable? {
    val painter = rememberAsyncImagePainter(
        Builder(LocalContext.current)
            .placeholder(R.drawable.placeholder)
            .data(photoUrl)
            .build()
    )
    Image(
        contentScale = contentScale,
        painter = painter,
        contentDescription = null,
        modifier = modifier,
    )
    val state = painter.state as? AsyncImagePainter.State.Success
    return state?.result?.drawable
}

@Composable
fun AppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.primary,
                    MaterialTheme.colors.onSecondary
                )
            )
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Rounded.ThumbUp,
                contentDescription = null,
                modifier = Modifier.padding(start = 12.dp)
            )
        },
        title = {
            Text(text = stringResource(string.app_name))
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    )
}

@Preview("Photo item")
@Composable
private fun FeaturedPostPreview() {
    UnsplashTheme {
        photoItem("")
    }
}
