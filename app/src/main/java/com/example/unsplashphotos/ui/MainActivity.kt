package com.example.unsplashphotos.ui

import android.app.DownloadManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.example.unsplashphotos.R
import com.example.unsplashphotos.data.repository.DownloaderUtils
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var downloaderUtils: DownloaderUtils
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListeners()
        setContent {
            navController = rememberAnimatedNavController()
            DestinationsNavHost(navGraph = NavGraphs.root)
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
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
