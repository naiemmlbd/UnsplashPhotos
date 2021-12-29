package com.example.unsplashphotos.ui.photofullscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unsplashphotos.R
import com.example.unsplashphotos.common.ImageLoader
import com.example.unsplashphotos.databinding.FragmentPhotoFullScreenBinding
import com.example.unsplashphotos.ui.ShareUtils.Companion.shareImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFullScreenFragment : Fragment() {
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var binding: FragmentPhotoFullScreenBinding
    private val photoFullViewModel by viewModels<PhotoFullViewModel>()
    private lateinit var downloadLink: String
    private lateinit var shareHtmlLink: String
    private var likes = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_photo_full_screen, container, false
        );
        binding.photoFullViewModel = photoFullViewModel
        binding.lifecycleOwner = this
        binding.saveFab.setOnClickListener {
            activityResultLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        binding.shareFab.setOnClickListener {
            share()
        }
        binding.infoFab.setOnClickListener {
            showInfo()
        }
        toggleActionBarVisibility()
        return binding.root
    }

    private fun toggleActionBarVisibility() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                photoFullViewModel.fabStateFlow.collectLatest {
                    if (it) {
                        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

                    } else {
                        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

                    }
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun showInfo() {
        Snackbar.make(binding.root, "Likes: $likes", Snackbar.LENGTH_LONG).show()
    }

    private fun share() {
        val bitmapDrawable = binding.imgPhoto.drawable as BitmapDrawable
        val photoId = photoFullViewModel.photoId
        if (photoId != null)
            shareImage(requireContext(), photoId, bitmapDrawable)
    }

    private fun sharePhotoLink() {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/html"
        share.putExtra(Intent.EXTRA_TEXT, shareHtmlLink)
        startActivity(Intent.createChooser(share, getString(R.string.sharevia)))
    }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                photoFullViewModel.onClickDownloadFab(downloadLink)
            } else {
                Toast.makeText(activity, getString(R.string.storageperm), Toast.LENGTH_SHORT).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayPhoto()
    }

    private fun displayPhoto() {
        binding.progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            photoFullViewModel.getPhotoById()
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoFullViewModel.stateFlow.collectLatest {
                    if (it != null) {
                        binding.photoFullScreen = it
                        downloadLink = it.links.download
                        shareHtmlLink = it.links.html
                        likes = it.likes
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.textTitle.text = getString(R.string.no_data)
                        Toast.makeText(activity, getString(R.string.no_data), Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}
