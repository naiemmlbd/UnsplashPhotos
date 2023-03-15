package com.example.unsplashphotos.ui

import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.photofullscreen.PhotoFullScreen
import com.example.unsplashphotos.ui.photofullscreen.PhotoFullViewModel

@Composable
fun UnsplashNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = UnsplashDestinations.GALLERY_ROUTE,
    photoViewModel: PhotoViewModel = hiltViewModel(),
    photoFullViewModel: PhotoFullViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val onPhotoClick = { photo: Photo ->
        navController.navigate("${UnsplashDestinations.FULL_SCREEN_ROUTE}/${photo.id}")
    }

    fun share(bitmapDrawable: BitmapDrawable?, photoId: String) {
        bitmapDrawable?.let {
            ShareUtils.shareImage(context = context, photoId, it)
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(UnsplashDestinations.GALLERY_ROUTE) {
            GalleryScreen(onPhotoClick = onPhotoClick, photos = photoViewModel.getPhotos())
        }

        composable(
            route = "${UnsplashDestinations.FULL_SCREEN_ROUTE}/{photoId}",
            arguments = listOf(
                navArgument("photoId") {
                    type = NavType.StringType
                }
            )
        ) {
            val photoId = it.arguments?.getString("photoId") ?: ""

            PhotoFullScreen(
                onShareClicked = { bitmapDrawable ->
                    share(bitmapDrawable, photoId)
                },
                photoId = photoId
            )
        }
    }
}
