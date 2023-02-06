package com.example.unsplashphotos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.unsplashphotos.databinding.FragmentGalleryBinding
import com.example.unsplashphotos.domain.model.Photo
import com.example.unsplashphotos.ui.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var binding: FragmentGalleryBinding
    private val photoViewModel by viewModels<PhotoViewModel>()

    private val onPhotoClicked = { selectedPhoto: Photo ->
        val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFullScreenFragment(
            selectedPhoto.id
        )
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComposeView()
    }

    private fun setupComposeView() {
        binding.composeView.apply {
            setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GalleryScreen(viewModel = photoViewModel, onPhotoClick = onPhotoClicked)
            }
        }
    }
}
