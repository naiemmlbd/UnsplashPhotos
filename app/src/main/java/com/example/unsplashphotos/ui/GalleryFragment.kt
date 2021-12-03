package com.example.unsplashphotos.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplashphotos.databinding.FragmentGalleryBinding
import com.example.unsplashphotos.ui.adapter.PhotoAdapter


class GalleryFragment : Fragment() {

    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var binding: FragmentGalleryBinding
    private val photoViewModel by viewModels<PhotoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoAdapter = PhotoAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPhotoRecyclerView()
    }

    private fun setupPhotoRecyclerView() {
        binding.photosRecyclerView.adapter = photoAdapter
        val spanCount = 2
        binding.photosRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), spanCount, GridLayoutManager.VERTICAL, false)

        displayPhotos()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun displayPhotos() {
        binding.photoProgressBar.visibility = View.VISIBLE
        val responseLiveData = photoViewModel.getPhotos()
        responseLiveData.observe(viewLifecycleOwner,{
            if (it != null){
                photoAdapter.setList(it)
                photoAdapter.notifyDataSetChanged()
                binding.photoProgressBar.visibility = View.GONE
            } else {
                binding.photoProgressBar.visibility = View.GONE
                binding.textView.visibility = View.VISIBLE
                binding.textView.text = "No Data Available"
                Toast.makeText(activity, "No data available", Toast.LENGTH_LONG).show()
            }
        })
    }
}