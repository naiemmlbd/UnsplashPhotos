package com.example.unsplashphotos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashphotos.data.model.local.Photo
import com.example.unsplashphotos.databinding.FragmentGalleryBinding
import com.example.unsplashphotos.ui.adapter.PhotoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var binding: FragmentGalleryBinding
    private val photoViewModel by viewModels<PhotoViewModel>()

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
        setupPhotoRecyclerView()
    }

    private fun setupPhotoRecyclerView() {
        photoAdapter = PhotoAdapter { selectedPhoto: Photo, extra ->
            val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoFullScreenFragment(
                selectedPhoto.id
            )
            findNavController().navigate(action)
        }

        photoAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.photosRecyclerView.adapter = photoAdapter
//        photoViewModel
        binding.photosRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        displayPhotos()
    }

    private fun displayPhotos() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoViewModel.photos.collect {
                    photoAdapter.submitData(it)
                }
            }
        }
    }
}
