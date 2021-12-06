package com.example.unsplashphotos.ui.photofullscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.unsplashphotos.common.ImageLoader
import com.example.unsplashphotos.databinding.FragmentPhotoFullScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoFullScreenFragment : Fragment() {
    private lateinit var binding: FragmentPhotoFullScreenBinding
    private val photoFullViewModel by viewModels<PhotoFullViewModel>()
    private var photoId: String = "ph"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoId = arguments?.getString("photoId") ?: "ph"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoFullScreenBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPhoto()
    }

    private fun displayPhoto() {

        binding.progressBar.visibility = View.VISIBLE

        val responseLiveData = photoFullViewModel.getPhotoById(photoId)
        responseLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.photoFullScreen = it
                loadPhoto(it.urls.regular)
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.textTitle.text = "No Data Available"
                Toast.makeText(activity, "No data available", Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun loadPhoto(url: String) {

        ImageLoader.instance?.load(binding.imgPhoto.context, url, binding.imgPhoto)

    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

