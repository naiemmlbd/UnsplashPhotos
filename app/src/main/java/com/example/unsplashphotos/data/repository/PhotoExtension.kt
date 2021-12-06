package com.example.unsplashphotos.data.repository

import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashphotos.data.model.Photo


val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

}
