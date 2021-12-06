package com.example.unsplashphotos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashphotos.R
import com.example.unsplashphotos.data.model.Photo
import com.example.unsplashphotos.data.repository.DIFF_ITEM_CALLBACK
import com.example.unsplashphotos.databinding.ItemPhotoBinding


class PhotoAdapter(private val selectListener: (Photo, FragmentNavigator.Extras) -> Unit) : PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DIFF_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPhotoBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_photo, parent, false)

        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, selectListener)
        }
    }


    class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, selectListener: (Photo,FragmentNavigator.Extras) -> Unit) {
            binding.photo = photo

            binding.root.setOnClickListener {
                val extras = FragmentNavigatorExtras(
                    binding.photoThumbnail to "image${photo.id}"
                )
                selectListener(photo, extras)
            }
        }

    }
}