package com.example.unsplashphotos.ui.photofullscreen

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unsplashphotos.R
import com.example.unsplashphotos.common.ImageLoader
import com.example.unsplashphotos.data.repository.DownloaderUtils
import com.example.unsplashphotos.databinding.FragmentPhotoFullScreenBinding
import com.example.unsplashphotos.ui.ShareUtils.Companion.shareImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class PhotoFullScreenFragment : Fragment() {
    companion object {
        private const val STORAGE_PERMISSION_CODE = 100
    }

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var downloaderUtils: DownloaderUtils
    private lateinit var binding: FragmentPhotoFullScreenBinding
    private val photoFullViewModel by viewModels<PhotoFullViewModel>()
    private lateinit var photoId: String
    private lateinit var imageView: ImageView
    private lateinit var downloadLink: String
    private lateinit var shareHtmlLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoId = arguments?.getString("photoId").toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoFullScreenBinding.inflate(inflater)
        imageView = binding.imgPhoto
        binding.saveFab.setOnClickListener {
            checkPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE
            )
        }

        binding.shareFab.setOnClickListener {
            shareImage(requireContext(), photoId, imageView)
        }
        return binding.root
    }

    private fun sharePhotoLink() {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "text/html"
        share.putExtra(Intent.EXTRA_TEXT, shareHtmlLink)
        startActivity(Intent.createChooser(share, getString(R.string.sharevia)))
    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
        } else {
            downloaderUtils.downloadPhoto(downloadLink, photoId)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayPhoto()
    }

    private fun displayPhoto() {
        binding.progressBar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            photoFullViewModel.getPhotoById(photoId)
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoFullViewModel.flow.collect {
                    if (it != null) {
                        binding.photoFullScreen = it
                        loadPhoto(it.urls.regular)
                        downloadLink = it.links.download
                        shareHtmlLink = it.links.html
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

    private fun loadPhoto(url: String) {
        imageLoader.load(url, binding.imgPhoto)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                downloaderUtils.downloadPhoto(downloadLink, photoId)
            } else {
                Toast.makeText(activity, getString(R.string.storageperm), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
