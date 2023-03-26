package com.example.unsplashphotos.ui

import android.app.DownloadManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.ui.Alignment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.unsplashphotos.R
import com.example.unsplashphotos.data.repository.DownloaderUtils
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var downloaderUtils: DownloaderUtils
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setContent {
            val navController = rememberAnimatedNavController()
            val navHostEngine = rememberAnimatedNavHostEngine(
                navHostContentAlignment = Alignment.TopCenter,
                rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                defaultAnimationsForNestedNavGraph = mapOf(
                    NavGraphs.root to NestedNavGraphDefaultAnimations(
                        enterTransition = { slideInHorizontally() },
                        exitTransition = { slideOutHorizontally() },
                    ),
                ),
            )
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                engine = navHostEngine,
            )
        }
    }

    private fun setupListeners() {
        downloaderUtils.downloadStatusListener = { downloadStatus, directory ->
            lifecycleScope.launch() {
                when (downloadStatus) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        Toast.makeText(
                            applicationContext,
                            applicationContext.getString(R.string.downloadSuccess) + " " + directory,
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }
        }
    }
}
